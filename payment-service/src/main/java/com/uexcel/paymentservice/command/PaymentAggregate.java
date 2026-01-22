package com.uexcel.paymentservice.command;

import com.uexcel.core.command.ProcessPaymentCommand;
import com.uexcel.core.command.event.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    public  PaymentAggregate(){}

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand command) {
        if(command.getPaymentId() == null || command.getOrderId() == null){
            throw new IllegalArgumentException("paymentId and/or orderId cannot be null");
        }
        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .paymentId(command.getPaymentId())
                .orderId(command.getOrderId())
                .build();
        AggregateLifecycle.apply(paymentProcessedEvent);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }

}
