package com.uexcel.productservice.rest;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductModel{
        private final String title;
        private final BigDecimal price;
        private final Integer quantity;
}
