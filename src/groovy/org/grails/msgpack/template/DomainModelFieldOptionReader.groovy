package org.grails.msgpack.template

import java.lang.reflect.Field;

import org.msgpack.template.BeansFieldEntry;
import org.msgpack.template.FieldOption;
import org.msgpack.template.FieldOptionReader;
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

            if(isBelongsTo(targetClass, entry)){
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

    /**
     * check if the entry is my owner class.<br />
     * for instance, if the class has 'User' class as 'user' property,<br />
     * and the property is marked as 'belongsTo',<br />
     * it means the class is owned by the User class.
     * 
     * @param targetClass
     * @param entry
     * @return
     */
    def isBelongsTo( Class<?> targetClass, BeansFieldEntry entry ){
        def belongsTo = getStaticPropertyValue(targetClass, 'belongsTo')
        belongsTo?.entrySet()?.value?.contains(entry.type)
    }


}
