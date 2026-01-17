package com.uexcel.orderservice.command.rest;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrderModel {
    private String productId;
    private Integer quantity;
    private String addressId;
}
