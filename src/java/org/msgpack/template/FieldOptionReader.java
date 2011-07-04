package org.msgpack.template;

public interface FieldOptionReader {
    FieldOption read( Class<?> targetClass,
            BeansFieldEntry entry, FieldOption implicitOption );
}
