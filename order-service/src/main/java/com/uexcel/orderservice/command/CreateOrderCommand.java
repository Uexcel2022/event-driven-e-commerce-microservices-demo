package com.uexcel.orderservice.command;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CreateOrderCommand {
    public final String orderId;
    private final String userId;
    private final String productId;
    private final Integer quantity;
    private final String addressId;
    private final OrderStatus orderStatus;
}
