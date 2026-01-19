package com.uexcel.core.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDetails {
    private final String name;
    private final String cardNumber;
    private final String validUntilMonth;
    private final String getValidUntilYear;
    private final String cvv;
}
