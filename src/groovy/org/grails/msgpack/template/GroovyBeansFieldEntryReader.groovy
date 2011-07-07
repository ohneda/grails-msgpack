package org.grails.msgpack.template;

import java.util.ArrayList

import org.apache.commons.logging.LogFactory;
import org.msgpack.template.BeansFieldEntry
import org.msgpack.template.BeansFieldEntryReader
import org.msgpack.template.FieldOption
import org.msgpack.template.FieldOptionReader
import org.msgpack.template.IFieldEntry

public class GroovyBeansFieldEntryReader extends BeansFieldEntryReader {

    private static final log = LogFactory.getLog(this)

    FieldOptionReader optionReader = GroovyBeansFieldOptionReader.instance

    @Override
    public IFieldEntry[] readFieldEntries( Class<?> targetClass,
            FieldOption implicitOption ) {

        BeansFieldEntry[] entries = (BeansFieldEntry[]) super.readFieldEntries(
                targetClass, implicitOption );

        ArrayList<BeansFieldEntry> list = new ArrayList<BeansFieldEntry>();
        entries.each{
            if ( it.getType() != groovy.lang.MetaClass.class ) {
                FieldOption op = optionReader.read( targetClass, it,
                        implicitOption );
                it.option = op
                log.debug("class ${targetClass} field ${it.name} set option ${op}")
                list.add( it );
            }

        }

        return list.toArray( new BeansFieldEntry[list.size()] );
    }

}
