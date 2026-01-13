package com.uexcel.productservice.command;

import com.uexcel.productservice.core.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
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

    public ProductAggregate() {}

    @CommandHandler
    public ProductAggregate(CreateProductCommand cmd) {
        // you can perform validation
        if (cmd.getProductId() == null || cmd.getTitle() == null || cmd.getPrice() == null) {
            throw new InvalidParameterException("Product Id and Title are mandatory");
        }
        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(cmd, event);
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
}
