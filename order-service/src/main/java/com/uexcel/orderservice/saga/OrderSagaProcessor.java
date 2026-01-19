package com.uexcel.orderservice.saga;

import com.uexcel.core.command.ReserveProductCommand;
import com.uexcel.core.command.event.ProductReservedEvent;
import com.uexcel.orderservice.core.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//A manager to for business concern that need to be grouped as one transaction

@Saga
public class OrderSagaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderSagaProcessor.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Saga started. OrderId: {}",orderCreatedEvent.getOrderId());
        ReserveProductCommand reserveProductCommand = ReserveProductCommand
                .builder()
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .build();
        commandGateway.send(reserveProductCommand,
                new CommandCallback<ReserveProductCommand, Object>() {
                    @Override
                    public void onResult(@NonNull CommandMessage<? extends
                            ReserveProductCommand> commandMessage,
                                         @NonNull CommandResultMessage<?> commandResultMessage) {
                        if(commandResultMessage.isExceptional()){
                            return;
                        }
                    }
                });
        logger.info("Saga Ended ProductRevered. OrderId: {}",orderCreatedEvent.getOrderId());

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        logger.info("Processing payment. Product ReservedId: {}",productReservedEvent.getProductId());

    }


}
