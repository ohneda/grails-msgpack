package org.grails.msgpack.util;

import static org.junit.Assert.*;
import grails.plugin.spock.IntegrationSpec;
import spock.lang.Specification;
import msgpack.*

class GrailsDomainPropertyUtilSpec extends IntegrationSpec {

    def "get static value by list"(){

        expect:
        GrailsDomainPropertyUtil.getStaticValuesWithList(msgpack.PropertyTestDomain.class, property) == result

        where:
        property          | result
        'singleBelongsTo' | [Message]
        'listBelongsTo'   | [Message, Role]
        'mapBelongsTo'    | [Message, Role, Comment]
        'notBelongsTo'    | []

    }

    def "get a generic type of a return value of fields"(){

        expect:
        def prop = targetClass.metaClass.properties.find{ it.name == property }
        GrailsDomainPropertyUtil.getReturnGenericType(targetClass, prop) == result

        where:
        targetClass        | property         | result
        PropertyTestDomain | 'listForGeneric' | [Message]
        PropertyTestDomain | 'justList'       | null
        PropertyTestDomain | 'mapForGeneric1' | [String, Message]
        PropertyTestDomain | 'mapForGeneric2' | [Role, Message]
        PropertyTestDomain | 'justMap'        | null
        PropertyTestDomain | 'setForGeneric'  | [Comment]
        PropertyTestDomain | 'justSet'        | null
        PropertyTestDomain | 'noProperty'     | null
        Message            | 'comments'       | [Comment]
        Message            | 'props'          | [String,String]
    }

    def "check if a field is marked as 'belongsTo'"(){

        expect:
        GrailsDomainPropertyUtil.isBelongsTo(targetClass, property) == result

        where:
        targetClass        | property  | result
        User               | 'message' | true
        User               | 'name'    | false
        User               | 'title'   | false
        PropertyTestDomain | 'role'    | true
        PropertyTestDomain | 'user'    | true
        PropertyTestDomain | 'justSet' | false

    }

}
