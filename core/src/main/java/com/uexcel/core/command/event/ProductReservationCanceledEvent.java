package com.uexcel.core.command.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReservationCanceledEvent {
    private String productId;
    private int quantity;
    private String orderId;
    private String userId;
    private String reason;
}
