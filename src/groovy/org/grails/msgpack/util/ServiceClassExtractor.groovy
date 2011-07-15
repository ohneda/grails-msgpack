package org.grails.msgpack.util

import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.GrailsClassUtils

/**
 * Extract the service class which is marked for exposing as MessagePack RPC
 * @author ohneda
  */
class ServiceClassExtractor {

    def grailsApplication
    private static final log = LogFactory.getLog(this)

    def extract(){
        def services = getMessagePackService(grailsApplication.serviceClasses)
        if( services.size() == 0 ){
            return null
        }
        if( services.size() > 1 ){
            log.warn("you can expose only one service class as MessagePack for now.")
        }
        log.info("expose ${services[0].fullName} as MessagePack RPC" )
        services[0]

    }

    def excludeMethodTemplateServiceClass(){
        grailsApplication.serviceClasses.find { it.fullName == 'org.grails.msgpack.DummyService' }
    }

    def getMessagePackService(services){
        services.grep{ service ->
            def exposes = GrailsClassUtils.getStaticPropertyValue(service.getClazz(), 'expose')
            exposes?.contains('msgpack')
        }
    }
}
