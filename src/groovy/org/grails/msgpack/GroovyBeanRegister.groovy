package org.grails.msgpack

import org.grails.msgpack.template.builder.GroovyBeansTemplateBuilderSelector
import org.msgpack.MessagePack
import org.msgpack.template.GroovyBeansFieldEntryReader
import org.msgpack.template.builder.BeansBuildContext
import org.msgpack.template.builder.BuildContextBase
import org.msgpack.template.builder.BuildContextFactory
import org.msgpack.template.builder.BuilderSelectorRegistry
import org.msgpack.template.builder.JavassistTemplateBuilder

@Singleton
class GroovyBeanRegister {

    private GroovyBeanRegister(){
        def registory = BuilderSelectorRegistry.getInstance()

        if(!registory.contains(GroovyBeansTemplateBuilderSelector.NAME)){
            registory.insert(0, new GroovyBeansTemplateBuilderSelector(
                    new JavassistTemplateBuilder(
                    new GroovyBeansFieldEntryReader(),
                    new BuildContextFactory() {
                        @Override
                        public BuildContextBase createBuildContext(JavassistTemplateBuilder builder) {
                            return new BeansBuildContext(builder);
                        }
                    }
                    )));
        }
    }

    public void register(Class<?> clazz){
        MessagePack.register(clazz)
    }
}
