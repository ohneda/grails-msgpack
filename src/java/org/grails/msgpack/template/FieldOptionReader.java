package org.grails.msgpack.template;

import org.msgpack.template.BeansFieldEntry;
import org.msgpack.template.FieldOption;

public interface FieldOptionReader {
    FieldOption read( Class<?> targetClass,
            BeansFieldEntry entry, FieldOption implicitOption );
}
