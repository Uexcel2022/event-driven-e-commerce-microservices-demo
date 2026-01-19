package com.uexcel.core.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Users {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final PaymentDetails paymentDetails;
}
