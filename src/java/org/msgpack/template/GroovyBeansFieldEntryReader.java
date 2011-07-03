package org.msgpack.template;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import org.msgpack.template.BeansFieldEntryReader;

public class GroovyBeansFieldEntryReader extends BeansFieldEntryReader {

    @Override
    public IFieldEntry[] readFieldEntries(Class<?> targetClass,
            FieldOption implicitOption) {

        BeansFieldEntry[] entries = (BeansFieldEntry[])super.readFieldEntries( targetClass, implicitOption );

        ArrayList<BeansFieldEntry> list = new ArrayList<BeansFieldEntry>();
        for(int i = 0; i < entries.length; i++){
            if(entries[i].getType() != groovy.lang.MetaClass.class ){
                list.add( entries[i] );
            }
        }
        return list.toArray( new BeansFieldEntry[list.size()] );
    }

    @Override
    boolean isIgnoreProp(PropertyDescriptor desc){

        if(desc == null ){
            return true;
        }

        if(desc.getPropertyType() == groovy.lang.MetaClass.class){
            return true;
        }

        return super.isIgnoreProp( desc );
    }
}
