package org.grails.msgpack.template.builder

class GrailsDomainModelTemplateBuilderSelectorSpec extends spock.lang.Specification {

    GrailsDomainModelTemplateBuilderSelector selector

    def setup(){
        selector = new GrailsDomainModelTemplateBuilderSelector()
    }

    def 'macthType return true if the target groovy class has static constraints fields'(){

        when:
        def result = selector.matchType(msgpack.Message.class)

        then:
        result == true
    }

    def 'macthType return false if the target groovy class does not static constraints fields'(){

        when:
        def result = selector.matchType(msgpack.beans.Message.class)

        then:
        result == false
    }

    def 'matchType return false if the non-groovy class passed'(){

        when:
        def result = selector.matchType(String.class)

        then:
        result == false
    }
}
