package org.grails.msgpack.util

import grails.plugin.spock.IntegrationSpec;

class ServiceClassExtractorSpec extends IntegrationSpec{

    def grailsApplication

    def "extract the service class which is marked as MessagePacK"(){

        given: 'Grails has three services, MessageService, NonMessagePackService, DummyService'
        and: 'MessageService has the expose field with "msgpack" value'
        when:
        ServiceClassExtractor extractor = new ServiceClassExtractor(grailsApplication: grailsApplication)
        def result = extractor.getMessagePackService(grailsApplication.serviceClasses)

        then:
        result
        result.size() == 1
        result[0].fullName == 'msgpack.MessageService'
    }

}
