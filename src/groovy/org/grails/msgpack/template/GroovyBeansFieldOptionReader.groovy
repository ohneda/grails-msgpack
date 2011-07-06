package org.grails.msgpack.template

import java.lang.reflect.Field;

import org.msgpack.annotation.Ignore;
import org.msgpack.annotation.Nullable;
import org.msgpack.annotation.Optional;
import org.msgpack.annotation.Required;
import org.msgpack.template.BeansFieldEntry;
import org.msgpack.template.FieldOption;
import org.msgpack.template.FieldOptionReader;

@Singleton
class GroovyBeansFieldOptionReader implements FieldOptionReader {

    @Override
    public FieldOption read( Class<?> targetClass,
            BeansFieldEntry entry, FieldOption implicitOption ){
        Field field = targetClass.getDeclaredField( entry.getName() );

        if ( field.isAnnotationPresent( Ignore.class ) ) {
            return FieldOption.IGNORE
        } else if ( field.isAnnotationPresent( Required.class ) ) {
            return FieldOption.REQUIRED
        } else if ( field.isAnnotationPresent( Optional.class ) ) {
            return FieldOption.OPTIONAL
        } else if ( field.isAnnotationPresent( Nullable.class ) ) {
            return FieldOption.NULLABLE
        }
        return implicitOption;
    }

}
