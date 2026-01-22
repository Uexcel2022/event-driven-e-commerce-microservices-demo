package com.uexcel.paymentservice.query;

import com.uexcel.core.command.event.PaymentProcessedEvent;
import com.uexcel.paymentservice.core.data.PaymentEntity;
import com.uexcel.paymentservice.core.data.PaymentsRepository;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentEventsHandler.class);
    private final PaymentsRepository paymentsRepository;

    public PaymentEventsHandler(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        PaymentEntity paymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(event, paymentEntity);
        paymentsRepository.save(paymentEntity);
        LOGGER.info("************* payment processed successfully**** {}", paymentEntity);
    }
}
