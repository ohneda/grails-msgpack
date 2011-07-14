package org.grails.msgpack.template

import org.grails.msgpack.util.GrailsDomainPropertyUtil
import org.msgpack.template.BeansFieldEntry
import org.msgpack.template.FieldOption
import org.msgpack.template.FieldOptionReader
import static org.codehaus.groovy.grails.commons.GrailsClassUtils.*
import static org.codehaus.groovy.grails.commons.GrailsDomainConfigurationUtil.*

@Singleton
public class DomainModelFieldOptionReader implements FieldOptionReader {

    @Override
    public FieldOption read( Class<?> targetClass, BeansFieldEntry entry,
            FieldOption implicitOption ) {

            if(['id', 'version'].contains(entry.name)){
                return FieldOption.OPTIONAL
            }

            if(GrailsDomainPropertyUtil.isBelongsTo(targetClass, entry.name)){
                return FieldOption.IGNORE
            }

            if(isOwner(targetClass, entry)){
                return FieldOption.IGNORE
            }

            def constraints = evaluateConstraints(targetClass)
            if(constraints?."${entry.name}"?.nullable){
                return FieldOption.OPTIONAL
            }

            def transients = getStaticPropertyValue(targetClass, 'transients')
            if(transients?.contains(entry.name)){
                return FieldOption.IGNORE
            }

            return implicitOption
    }

    /**
     * check if the entry is a owner class of the target class itself. <br />
     * for instance, if the class which target class has in it also have the target class as 'hasOne' in it, <br />
     * it means the class of the entry owns the target class.
     * 
     * @param targetClass
     * @param entry
     * @return
     */
    def isOwner(Class<?> targetClass, BeansFieldEntry entry ){
        def hasOne = getStaticPropertyValue(entry.type, 'hasOne')
        hasOne?.entrySet()?.value?.contains(targetClass)
    }

}
