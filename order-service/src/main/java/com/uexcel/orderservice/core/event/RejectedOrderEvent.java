package com.uexcel.orderservice.core.event;

import com.uexcel.orderservice.command.OrderStatus;
import lombok.Value;

@Value
public class RejectedOrderEvent {
    String orderId;
    String reason;
    OrderStatus orderStatus;
}
