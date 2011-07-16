package org.grails.msgpack.template.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.msgpack.template.builder.BuilderSelector;
import org.msgpack.template.builder.TemplateBuilder;

public class GrailsDomainModelTemplateBuilderSelector implements BuilderSelector {

    public static final String NAME = "GrailsDomainModelTemplateBuilder";

    TemplateBuilder builder;

    public GrailsDomainModelTemplateBuilderSelector(TemplateBuilder builder){
        this.builder = builder;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public boolean matchType(Type targetType) {
        Class<?> targetClass = (Class<?>)targetType;

        try {
            Field field = targetClass.getDeclaredField( "constraints" );
            if( !Modifier.isStatic( field.getModifiers() )){
                return false;
            }

        } catch ( SecurityException e ) {
            return false;
        } catch ( NoSuchFieldException e ) {
            return false;
        }

        return groovy.lang.GroovyObject.class.isAssignableFrom( targetClass );

    }

    @Override
    public TemplateBuilder getTemplateBuilder(Type targetType) {
        return builder;
    }

}
