package org.grails.msgpack.template

import org.apache.commons.logging.LogFactory
import org.msgpack.template.BeansFieldEntry
import org.msgpack.template.BeansFieldEntryReader
import org.msgpack.template.FieldOption
import org.msgpack.template.IFieldEntry

public class GroovyBeansFieldEntryReader extends BeansFieldEntryReader {

    private static final log = LogFactory.getLog(this)

    FieldOptionReader optionReader = GroovyBeansFieldOptionReader.instance

    @Override
    public IFieldEntry[] readFieldEntries( Class<?> targetClass,
    FieldOption implicitOption ) {

        // TODO: refactor
        BeansFieldEntry[] entries = (BeansFieldEntry[]) super.readFieldEntries(
                targetClass, implicitOption );

        ArrayList<BeansFieldEntry> list = []
        entries.each{
            if ( it.type != groovy.lang.MetaClass ) {
                def field = evaluateFieldOption(targetClass, it, implicitOption)
                if(field.option != org.msgpack.template.FieldOption.IGNORE){
                    list.add( field )
                }
            }

        }

        return list.toArray( new BeansFieldEntry[list.size()] );
    }

    def evaluateFieldOption(targetClass, field, implicitOption){
        field.option = optionReader.read( targetClass, field,
                implicitOption )
        log.debug("class ${targetClass} field ${field.name} set option ${field.option}")
        field
    }

}
