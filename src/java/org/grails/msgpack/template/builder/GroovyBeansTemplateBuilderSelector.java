package org.grails.msgpack.template.builder;

import java.lang.reflect.Type;

import org.msgpack.annotation.MessagePackBeans;
import org.msgpack.template.builder.AnnotationTemplateBuilderSelector;
import org.msgpack.template.builder.BuilderSelector;
import org.msgpack.template.builder.TemplateBuilder;

public class GroovyBeansTemplateBuilderSelector implements BuilderSelector {

    public static final String NAME = "GroovyBeansTemplateBuilder";

    TemplateBuilder builder;

    public GroovyBeansTemplateBuilderSelector(TemplateBuilder builder){
        this.builder = builder;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public boolean matchType(Type targetType) {
        Class<?> targetClass = (Class<?>)targetType;

        if(!AnnotationTemplateBuilderSelector.isAnnotated(targetClass, MessagePackBeans.class)){
            return false;
        }

        return groovy.lang.GroovyObject.class.isAssignableFrom( targetClass );

    }

    @Override
    public TemplateBuilder getTemplateBuilder(Type targetType) {
        return builder;
    }

}
