package org.grails.msgpack.template;

import org.grails.msgpack.template.DomainModelFieldOptionReader
import org.grails.msgpack.template.GroovyBeansFieldEntryReader
import org.msgpack.template.BeansFieldEntryReader
import org.msgpack.template.IFieldEntryReader

public class EntityFieldReaderFactory {

    public static IFieldEntryReader getJavaBeansFieldEntryReader(){
        new BeansFieldEntryReader();
    }

    public static IFieldEntryReader getGroovyBeansFieldEntryReader(){
        new GroovyBeansFieldEntryReader()
    }

    public static IFieldEntryReader getGrailsDomainModelFieldEntryReader(){
        new GroovyBeansFieldEntryReader(optionReader: DomainModelFieldOptionReader.instance)
    }

}
