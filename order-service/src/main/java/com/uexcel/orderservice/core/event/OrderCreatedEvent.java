package com.uexcel.orderservice.core.event;

import com.uexcel.orderservice.command.OrderStatus;
import lombok.Data;

@Data
public class OrderCreatedEvent {
    public  String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private String addressId;
    private OrderStatus orderStatus;

}
