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

            def constraints = evaluateConstraints(targetClass)
            if(constraints?."${entry.name}"?.nullable){
                return FieldOption.OPTIONAL
            }

            def transients = getStaticPropertyValue(targetClass, 'transients')
            if(transients?.contains(entry.name)){
                return FieldOption.IGNORE
            }

            if(isBelongsTo(targetClass, entry)){
                return FieldOption.IGNORE
            }

            return implicitOption
    }

    def isBelongsTo( Class<?> targetClass, BeansFieldEntry entry ){
        def belongsTo = getStaticPropertyValue(targetClass, 'belongsTo')
        belongsTo?.keySet()?.contains(entry.name)
    }

}
