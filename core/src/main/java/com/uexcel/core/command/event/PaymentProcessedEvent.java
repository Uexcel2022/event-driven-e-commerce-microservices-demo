package com.uexcel.core.command.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProcessedEvent {
    private String orderId;
    private String paymentId;
}
