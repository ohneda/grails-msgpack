import org.msgpack.rpc.Server
import org.msgpack.rpc.loop.EventLoop
import grails.util.Environment
import org.grails.msgpack.util.ServiceClassExtractor
import org.grails.msgpack.util.ServiceProxyFactory

class MessagePackBootStrap {
    def grailsApplication
    Server svr

    def init = { servletContext ->

        if(grailsApplication.config.msgpack?.rpc?.expose){
          startMsgpackRpcServer()
        }
    }

    def destroy = {
        if(svr){
          svr.close();
          svr.getEventLoop().shutdown();
        }
    }

    def startMsgpackRpcServer(){
        def extractor = new ServiceClassExtractor(grailsApplication:grailsApplication)
        def targetService = extractor.extract()
        if(targetService){
            ServiceProxyFactory factory = new ServiceProxyFactory(target: targetService)
            factory.registerExcludeMethods(extractor.excludeMethodTemplateServiceClass())
            factory.registerPropertyMethods()
            def proxy = factory.getProxy()
            assert proxy
            proxy.target = grailsApplication.mainContext.getBean(targetService.propertyName)
            EventLoop loop = EventLoop.defaultEventLoop()
            svr = new Server(loop)
            svr.serve(proxy)
            svr.listen(grailsApplication.config.msgpack?.rpc?.port ?: 1985)
            log.info("MessagePack RPC server starts.")
        }
    }
}