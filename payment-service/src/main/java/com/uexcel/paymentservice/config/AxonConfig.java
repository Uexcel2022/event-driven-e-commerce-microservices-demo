package com.uexcel.paymentservice.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(NoTypePermission.NONE);
        xStream.allowTypesByWildcard(new String[] {
                "com.uexcel.core.command.*",
                "com.uexcel.core.command.event.*",
                "com.uexcel.paymentservice.core.event.*",
                "com.uexcel.paymentservice.command.*",
                "com.uexcel.paymentservice.command.rest.*",
                "com.uexcel.paymentservice.query.*",
                "java.util.*", "java.lang.*"
        });
        return xStream;
    }

    @Bean
    public Serializer messageSerializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}
