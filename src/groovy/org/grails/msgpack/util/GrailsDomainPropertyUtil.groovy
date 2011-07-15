package org.grails.msgpack.util

import static org.codehaus.groovy.grails.commons.GrailsClassUtils.*
import static org.codehaus.groovy.grails.commons.GrailsDomainConfigurationUtil.*

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class GrailsDomainPropertyUtil {

    /**
     * Returns true if the specified clazz property is marked as 'belongsTo' the clazz.
     * @param clazz
     * @param property
     * @return True if the property belongs to the claszz
     */
    static boolean isBelongsTo(Class targetClass, String propertyName){

        def prop = targetClass.metaClass.properties.find{ it.name == propertyName }

        def types = getReturnGenericType(targetClass, prop) ?: [prop.field.type]
        def values = this.getStaticValuesWithList(targetClass, 'belongsTo')
        for(int i = 0; i < types.size(); i++ ){
            if(values?.contains(types[i])){
                return true
            }
        }

        def keys = this.getStaticKeysWithList(targetClass, 'belongsTo')

        if( keys.contains(prop.name)){
            return true
        }

        false
    }

    /**
     * return the generic type if the specified property is defined with generic type. 
     * @param targetClass
     * @param propertyName
     * @return
     */
    static List<Type> getReturnGenericType(Class targetClass, MetaBeanProperty prop){

        def genericType = prop?.field?.field?.genericType

        if(genericType instanceof ParameterizedType){
            return genericType.actualTypeArguments
        }
        null
    }

    /**
     * return the key set if the specified static property is defined as Map
     * @param targetClass
     * @param propertyName
     * @return
     */
    static Set getStaticKeysWithList(Class targetClass, String propertyName){
        def values = getStaticPropertyValue(targetClass, propertyName)
        if( values instanceof Map ){
             return values.keySet()
        }
        []
    }

    /**
    * return the value list if the specified static property.
    * if the property is defined as Map, this method extract map values by List.
    * @param targetClass
    * @param propertyName
    * @return
    */
    static List getStaticValuesWithList(Class targetClass, String propertyName){
        def values = getStaticPropertyValue(targetClass, propertyName)

        switch (values) {
            case Map:
                values?.entrySet()?.value
                break
            case List:
                values
                break
            case null:
                []
                break
            default:
                [values]
        }

    }

}
