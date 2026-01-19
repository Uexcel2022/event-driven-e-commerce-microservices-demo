package com.uexcel.productservice.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {

    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager ptm) {
        return new SpringTransactionManager(ptm);

    }

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(NoTypePermission.NONE);
        xStream.allowTypesByWildcard(new String[] {
                "com.uexcel.core.command.*",
                "com.uexcel.core.command.event.*",
                "com.uexcel.productservice.core.event.*",
                "com.uexcel.productservice.command.*",
                "com.uexcel.productservice.command.interceptor.*",
                "com.uexcel.productservice.command.rest.*",
                "com.uexcel.productservice.query.*",
                "com.uexcel.productservice.query.rest.*",
                "java.util.*", "java.lang.*"
        });
        return xStream;
    }

    @Bean
    public Serializer messageSerializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();
    }







}
