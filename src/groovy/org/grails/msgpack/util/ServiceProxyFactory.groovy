package org.grails.msgpack.util

import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.GrailsClass
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import java.lang.reflect.Method
import javassist.*
import org.codehaus.groovy.reflection.CachedMethod
import org.grails.msgpack.GroovyBeansRegister;

/**
 * Generate Service Class Proxy.
 * @author ohneda
 *
 */
class ServiceProxyFactory {

    private static final log = LogFactory.getLog(this)

    def excludeMethods = []
    def target
    ClassPool pool = ClassPool.getDefault()

    def registerExcludeMethods(GrailsClass templateClass){
        excludeMethods.addAll( templateClass.metaClass.methods.name)
    }

    def registerPropertyMethods(){
        target.metaClass.properties.name.each{
            excludeMethods.addAll GrailsClassUtils.getGetterName(it)
            excludeMethods.addAll GrailsClassUtils.getSetterName(it)
        }
    }

    def extractMethods(){
        def manualExcludeMethods = GrailsClassUtils.getStaticPropertyValue(target.clazz, 'exclude')
        def targetMethodNames = target.metaClass.methods.name - (excludeMethods + manualExcludeMethods)
        target.metaClass.methods.grep{ it.name in targetMethodNames }
    }

    def addMethod(CtClass target, CachedMethod original){
        def params = []
        original.parameterTypes.each{ params << (CtClass)pool.get(it.name) }

        log.info("add method: ${original.returnType.name} ${original.name}(${original.getPT().name.join(',')})")
        if(!(original.returnType == Void.TYPE) && !(original.returnType.package.name.startsWith('java'))){
            GroovyBeansRegister.instance.register(original.returnType)
        }

        def returnType = pool.get(original.returnType.name)
        CtMethod m = CtNewMethod.make( original.modifiers,
                returnType,
                original.name,
                (CtClass[])params.toArray(),
                null,
                original.returnType == Void.TYPE ? "target.${methodString(original)};": "return target.${methodString(original)};",
                target)
        target.addMethod(m)
    }

    def methodString(CachedMethod method){
        def params = []
        method.parameterTypes.size().times{ params << "\$${it+1}" }
        "${method.name}(${params.join(',')})"
    }

    def getProxy(){

        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));

        try{
            CtClass current = pool.get("${target.fullName}Proxy" )
            current.detach();
        }catch(NotFoundException e){
            // do nothing
        }

        CtClass cc = pool.makeClass("${target.fullName}Proxy")
        //CtClass cc = cp.get("${target.fullName}Proxy")
        CtField f1 = CtField.make("${target.fullName} target;", cc)
        cc.addField(f1)
        extractMethods().each{ addMethod( cc, it ) }

        Class proxy = cc.toClass()
        proxy.newInstance()
    }

}
