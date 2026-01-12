package com.uexcel.product.command;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Builder
@Data
public class CreateProductCommand {
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;
}
