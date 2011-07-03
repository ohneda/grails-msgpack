package org.msgpack.template;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.msgpack.annotation.Ignore;
import org.msgpack.annotation.Nullable;
import org.msgpack.annotation.Optional;
import org.msgpack.annotation.Required;
import org.msgpack.template.BeansFieldEntryReader;

public class GroovyBeansFieldEntryReader extends BeansFieldEntryReader {

    @Override
    public IFieldEntry[] readFieldEntries( Class<?> targetClass,
            FieldOption implicitOption ) {

        BeansFieldEntry[] entries = (BeansFieldEntry[]) super.readFieldEntries(
                targetClass, implicitOption );

        ArrayList<BeansFieldEntry> list = new ArrayList<BeansFieldEntry>();
        for ( int i = 0; i < entries.length; i++ ) {
            if ( entries[i].getType() != groovy.lang.MetaClass.class ) {
                BeansFieldEntry e = entries[i];
                FieldOption op = readPropOption( targetClass, e,
                        implicitOption );
                e.setOption( op );
                list.add( entries[i] );
            }

        }

        return list.toArray( new BeansFieldEntry[list.size()] );
    }

    private FieldOption readPropOption( Class<?> targetClass,
            BeansFieldEntry entry, FieldOption implicitOption ) {

        Field field;
        try {
            field = targetClass.getDeclaredField( entry.getName() );
        } catch ( SecurityException e ) {
            return implicitOption;
        } catch ( NoSuchFieldException e ) {
            return implicitOption;
        }

        if ( field.isAnnotationPresent( Ignore.class ) ) {
            return FieldOption.IGNORE;
        } else if ( field.isAnnotationPresent( Required.class ) ) {
            return FieldOption.IGNORE;
        } else if ( field.isAnnotationPresent( Optional.class ) ) {
            return FieldOption.OPTIONAL;
        } else if ( field.isAnnotationPresent( Nullable.class ) ) {
            return FieldOption.NULLABLE;
        }
        return implicitOption;

    }

}
