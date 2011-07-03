package org.grails.msgpack.template.builder;

import java.lang.reflect.Type;

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

        // TODO decide if necessary MessagePackBeans annotation or not
        // AnnotationTemplateBuilderSelector.isAnnotated(targetClass, MessagePackBeans.class);

        // it doens't check if the targetType has MessagePackBeans annotation for now.
        boolean result = groovy.lang.GroovyObject.class.isAssignableFrom( targetClass );
        return result;
    }

    @Override
    public TemplateBuilder getTemplateBuilder(Type targetType) {
        return builder;
    }

}
