package org.grails.msgpack.template

import java.lang.reflect.Field;

import org.msgpack.template.BeansFieldEntry;
import org.msgpack.template.FieldOption;
import org.msgpack.template.FieldOptionReader;

@Singleton
public class DomainModelFieldOptionReader implements FieldOptionReader {

    @Override
    public FieldOption read( Class<?> targetClass, BeansFieldEntry entry,
            FieldOption implicitOption ) {

            if(['id', 'version'].contains(entry.name)){
                return FieldOption.OPTIONAL
            }

            if(msgpack.Message.constraints."${entry.name}"?.nullable){
                return FieldOption.OPTIONAL
            }

            return FieldOption.DEFAULT
    }

}
