package com.uexcel.orderservice.core.event;

import com.uexcel.orderservice.command.OrderStatus;
import lombok.Value;

@Value
public class OrderApprovedEvent {
    String orderId;
    OrderStatus orderStatus=OrderStatus.APPROVED;
}
