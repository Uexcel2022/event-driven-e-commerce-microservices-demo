package com.uexcel.orderservice.saga;

import com.uexcel.core.command.CancelProductReservationCommand;
import com.uexcel.core.command.ProcessPaymentCommand;
import com.uexcel.core.command.ReserveProductCommand;
import com.uexcel.core.command.event.PaymentProcessedEvent;
import com.uexcel.core.command.event.ProductReservationCanceledEvent;
import com.uexcel.core.command.event.ProductReservedEvent;

import com.uexcel.core.model.Users;
import com.uexcel.core.query.FetchUserPaymentDetailsQuery;
import com.uexcel.orderservice.command.commands.ApprovedOrderCommand;
import com.uexcel.orderservice.command.commands.RejectOrderCommand;
import com.uexcel.orderservice.core.event.OrderApprovedEvent;
import com.uexcel.orderservice.core.event.OrderCreatedEvent;

import com.uexcel.orderservice.core.event.RejectedOrderEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//A manager to for business concern that need to be grouped as one transaction

@Saga
public class OrderSagaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderSagaProcessor.class);

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

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

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        FetchUserPaymentDetailsQuery query = new FetchUserPaymentDetailsQuery(productReservedEvent.getOrderId());
        Users user = null;
       try {
          user  = queryGateway.query(query, ResponseTypes.instanceOf(Users.class)).join();
       }catch (Exception ex){
           logger.error(ex.getMessage(),ex);
           canceledProductReservation(productReservedEvent, ex.getLocalizedMessage());
           return;
       }

       if(user==null){
           canceledProductReservation(productReservedEvent, "Could fetch user payment details.");
           return;
       }

        logger.info("User Details {}",user);

       ProcessPaymentCommand paymentCommand = ProcessPaymentCommand.builder()
               .paymentId(UUID.randomUUID().toString())
               .orderId(productReservedEvent.getOrderId())
               .paymentDetails(user.getPaymentDetails())
               .build();

       String result = null;
       try{
          result =  commandGateway.sendAndWait(paymentCommand,10,TimeUnit.SECONDS);
       } catch (Exception ex){
           logger.error(ex.getMessage(),ex);
           canceledProductReservation(productReservedEvent, ex.getLocalizedMessage());
       }

       if(result==null){
           logger.error("{} not found", productReservedEvent.getOrderId());
           canceledProductReservation(productReservedEvent, "Payment process failed.");
       }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent){
      ApprovedOrderCommand approvedOrderCommand =
              new ApprovedOrderCommand(paymentProcessedEvent.getOrderId());
      commandGateway.send(approvedOrderCommand);
    }
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent){
        logger.info("************Order processed successfully***************");
//        SagaLifecycle.end(); to end sage programmatically
    }


    public void canceledProductReservation(ProductReservedEvent productReservedEvent, String reason) {

        CancelProductReservationCommand cpc = CancelProductReservationCommand.builder()
                .productId(productReservedEvent.getProductId())
                .orderId(productReservedEvent.getOrderId())
                .userId(productReservedEvent.getUserId())
                .quantity(productReservedEvent.getQuantity())
                .reason(reason)
                .build();
        commandGateway.send(cpc);

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void  handle(ProductReservationCanceledEvent pCRE){
        RejectOrderCommand rOC = new
                RejectOrderCommand(pCRE.getOrderId(), pCRE.getUserId());
        commandGateway.send(rOC);

    }
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(RejectedOrderEvent rejectedOrderEvent){
        logger.info("************Order was rejected successfully***************");
    }


}
