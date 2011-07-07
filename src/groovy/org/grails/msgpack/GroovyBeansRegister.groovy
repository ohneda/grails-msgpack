package org.grails.msgpack

import org.grails.msgpack.template.builder.GrailsDomainModelTemplateBuilderSelector;
import org.grails.msgpack.template.builder.GroovyBeansTemplateBuilderSelector
import org.msgpack.MessagePack
import org.grails.msgpack.template.EntityFieldReaderFactory;
import org.grails.msgpack.template.GroovyBeansFieldEntryReader
import org.msgpack.template.builder.BeansBuildContext
import org.msgpack.template.builder.BuildContextBase
import org.msgpack.template.builder.BuildContextFactory
import org.msgpack.template.builder.BuilderSelectorRegistry
import org.msgpack.template.builder.JavassistTemplateBuilder

@Singleton
class GroovyBeansRegister {

    private GroovyBeansRegister(){
        def registory = BuilderSelectorRegistry.getInstance()

        if(!registory.contains(GrailsDomainModelTemplateBuilderSelector.NAME)){
            registory.insert(0, new GrailsDomainModelTemplateBuilderSelector(
                    new JavassistTemplateBuilder(
                        EntityFieldReaderFactory.getGrailsDomainModelFieldEntryReader(),
                    new BuildContextFactory() {
                        @Override
                        public BuildContextBase createBuildContext(JavassistTemplateBuilder builder) {
                            return new BeansBuildContext(builder);
                        }
                    }
                    )));
        }
        if(!registory.contains(GroovyBeansTemplateBuilderSelector.NAME)){
            registory.insert(0, new GroovyBeansTemplateBuilderSelector(
                    new JavassistTemplateBuilder(
                        EntityFieldReaderFactory.getGroovyBeansFieldEntryReader(),
                    new BuildContextFactory() {
                        @Override
                        public BuildContextBase createBuildContext(JavassistTemplateBuilder builder) {
                            return new BeansBuildContext(builder);
                        }
                    }
                    )));
        }
    }

    public void register(Class<?> clazz){
        MessagePack.register(clazz)
    }
}
