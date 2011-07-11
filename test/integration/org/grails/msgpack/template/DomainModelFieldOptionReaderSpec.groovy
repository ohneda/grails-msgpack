package org.grails.msgpack.template

import java.util.Date;
import java.util.List;
import java.util.Map;

import grails.plugin.spock.IntegrationSpec;

import msgpack.Comment;
import msgpack.User;

import org.msgpack.template.BeansFieldEntry;
import org.msgpack.template.FieldOption;
import org.gmock.WithGMock;
import spock.lang.Specification;

@WithGMock
class DomainModelFieldOptionReaderSpec extends IntegrationSpec {

    DomainModelFieldOptionReader reader

    def setup(){
        reader = DomainModelFieldOptionReader.instance
    }

    def "the field which can be nullable in constraints is treated as Optional"(){

        given:
        def field = mock(BeansFieldEntry)
        field.name.returns(fieldName).stub()
        field.type.returns(classType).stub()
        FieldOption result
        play{
            result = reader.read( msgpack.Message, field, FieldOption.DEFAULT )
        }

        expect:
        result == option

        where:
        fieldName     | classType     | option
        'note'        | String.class  | FieldOption.OPTIONAL
        'dateUpdated' | Date.class    | FieldOption.OPTIONAL
        'postalId'    | Integer.class | FieldOption.OPTIONAL
    }

    def 'the field which can be blank in constraints is treated as Default'(){

        given:
        def field = mock(BeansFieldEntry)
        field.name.returns('body').stub()
        field.type.returns(String.class).stub()

        when:
        FieldOption result
        play{
            result = reader.read( msgpack.Message, field, FieldOption.DEFAULT )
        }

        then:
        result == FieldOption.DEFAULT
    }

    def 'the field which is not difnied in constraints is treated as Default'(){

        given:
        def field = mock(BeansFieldEntry)
        field.name.returns(fieldName).stub()
        field.type.returns(classType).stub()
        FieldOption result
        play{
            result = reader.read( msgpack.Message, field, FieldOption.DEFAULT )
        }

        expect:
        result == option

        where:
        fieldName     | classType     | option
        'dateCreated' | Date.class    | FieldOption.DEFAULT
        'owner'       | User.class    | FieldOption.DEFAULT
        'price'       | Float.class   | FieldOption.DEFAULT
        'readCount'   | Integer.class | FieldOption.DEFAULT
        'isPublic'    | Boolean.class | FieldOption.DEFAULT
    }

    def 'id field and version field, which is added by grails, is treated as Optional'(){

        given:
        def field = mock(BeansFieldEntry)
        field.name.returns(fieldName).stub()
        field.type.returns(classType).stub()
        FieldOption result
        play{
            result = reader.read( msgpack.Message, field, FieldOption.DEFAULT )
        }

        expect:
        result == option

        where:
        fieldName | classType  | option
        'id'      | Long.class | FieldOption.OPTIONAL
        'version' | Long.class | FieldOption.OPTIONAL
    }

    def 'the field which is marked as transient is treated as Ignore'(){

        given:
        def field = mock(BeansFieldEntry)
        field.name.returns(fieldName).stub()
        field.type.returns(String.class).stub()
        FieldOption result
        play{
            result = reader.read( msgpack.Message, field, FieldOption.DEFAULT )
        }

        expect:
        result == option

        where:
        fieldName       | classType    | option
        'transientProp' | String.class | FieldOption.IGNORE
        'unusedField'   | String.class | FieldOption.IGNORE
    }

    def 'the field which belongsTo parent class is treated as Ignore'(){

        // temporary
        // I'm not sure if this specification is appropriate.
        // Ideally, Packer could solve circular reference problem.
        given:
        def field = mock(BeansFieldEntry)
        field.name.returns(fieldName).stub()
        field.type.returns(classType).stub()
        FieldOption result
        play{
            result = reader.read( msgpack.User, field, FieldOption.DEFAULT )
        }

        expect:
        result == option

        where:
        fieldName | classType     | option
        'message' | msgpack.Message.class | FieldOption.IGNORE
    }
}
