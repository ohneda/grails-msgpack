package org.grails.msgpack.template.builder

class GroovyBeansTemplateBuilderSelectorSpec extends spock.lang.Specification {

    GroovyBeansTemplateBuilderSelector selector

    def setup(){
        selector = new GroovyBeansTemplateBuilderSelector()
    }

    def 'macthType return true if the target groovy class does not have MessagePackBean annotation'(){

        when:
        def result = selector.matchType(msgpack.Message.class)

        then:
        result == false
    }

    def 'macthType return false if the target groovy class has MessagePackBean annotation'(){

        when:
        def result = selector.matchType(msgpack.beans.Message.class)

        then:
        result == true
    }

    def 'matchType return false if the non-groovy class passed'(){

        when:
        def result = selector.matchType(String.class)

        then:
        result == false
    }
}
