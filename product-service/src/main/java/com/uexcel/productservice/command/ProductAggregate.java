package com.uexcel.productservice.command;
import com.uexcel.core.command.ReserveProductCommand;
import com.uexcel.core.command.event.ProductReservedEvent;
import com.uexcel.productservice.core.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public ProductAggregate() {}

    @CommandHandler
    public ProductAggregate(CreateProductCommand cmd){
//         you can perform validation
        if (cmd.getProductId() == null || cmd.getTitle() == null || cmd.getPrice() == null) {
            throw new InvalidParameterException("Product Id and Title are mandatory");
        }
        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(cmd, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        if(this.quantity < reserveProductCommand.getQuantity()){
            throw new IllegalStateException("Insufficient number of items in stock.");
        }
        ProductReservedEvent event  = ProductReservedEvent.builder()
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .orderId(reserveProductCommand.getOrderId())
                .userId(reserveProductCommand.getUserId())
                .build();
        logger.info("*********Aggregate applied *********");

        AggregateLifecycle.apply(event);
    }

    //Avoid adding business logic
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        logger.info("************Product Event sourcing executed productId {}", productReservedEvent.getProductId());
        this.quantity -= productReservedEvent.getQuantity();
    }
}
