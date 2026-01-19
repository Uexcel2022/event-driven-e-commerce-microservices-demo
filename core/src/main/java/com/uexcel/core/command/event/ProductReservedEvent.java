package com.uexcel.core.command.event;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ProductReservedEvent{
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
}
