package org.grails.msgpack.template

import static org.msgpack.template.FieldOption.*

import java.lang.reflect.Field
import org.msgpack.annotation.Ignore
import org.msgpack.annotation.Nullable
import org.msgpack.annotation.Optional
import org.msgpack.annotation.Required
import org.msgpack.template.BeansFieldEntry
import org.msgpack.template.FieldOption

@Singleton
class GroovyBeansFieldOptionReader implements FieldOptionReader {

    @Override
    public FieldOption read( Class<?> targetClass,
            BeansFieldEntry entry, FieldOption implicitOption ){

        Field field = targetClass.getDeclaredField( entry.name )

        if ( field.isAnnotationPresent( Ignore ) ) {
            return IGNORE
        }

        if ( field.isAnnotationPresent( Required ) ) {
            return REQUIRED
        }

        if ( field.isAnnotationPresent( Optional ) ) {
            return OPTIONAL
        }

        if ( field.isAnnotationPresent( Nullable ) ) {
            return NULLABLE
        }

        implicitOption
    }

}
