class MsgpackGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/controllers/msgpack/MessageController.groovy",
            "grails-app/domain/msgpack/Comment.groovy",
            "grails-app/domain/msgpack/Message.groovy",
            "grails-app/domain/msgpack/User.groovy",
            "grails-app/domain/msgpack/Role.groovy",
            "grails-app/domain/msgpack/PropertyTestDomain.groovy",
            "src/groovy/msgpack/beans/Comment.groovy",
            "src/groovy/msgpack/beans/Message.groovy",
            "src/groovy/msgpack/beans/User.groovy",
            "src/groovy/msgpack/beans/Role.groovy",
            "src/groovy/org/grails/msgpack/util/DataTransferObjectFactoryBean.groovy",
            "src/groovy/org/grails/msgpack/util/ClassPathForGeneratedClasses.groovy",
            "grails-app/services/msgpack/MessageService.groovy",
            "grails-app/services/msgpack/NonMessagePackService.groovy",
    ]

    // TODO Fill in these fields
    def author = "Yuichi Ohneda"
    def authorEmail = "ohneda@gmail.com"
    def title = "MessagePack for Grails"
    def description = '''\\
This plugin enable a Grails services to be exposed as MessagePack via MessagePack-RPC.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/msgpack"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
