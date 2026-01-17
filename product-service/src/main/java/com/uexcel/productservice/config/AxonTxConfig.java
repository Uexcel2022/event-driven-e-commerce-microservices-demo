package com.uexcel.productservice.config;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonTxConfig {
    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager ptm) {
        return new SpringTransactionManager(ptm);

    }
}
