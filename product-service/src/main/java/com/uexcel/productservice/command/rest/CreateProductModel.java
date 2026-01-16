package com.uexcel.productservice.command.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class CreateProductModel{
        @Length(min=5,max=100)
        @NotNull(message = "Product title filed is required.")
        private final String title;
        @Min(value = 1,message = "Price muse be greater than zero.")
        @NotNull(message = "Product price filed is required.")
        private final BigDecimal price;
        @PositiveOrZero(message = "Negative value for quantity is not allowed.")
        @NotNull(message = "Product quantity filed is required.")
        private final Integer quantity;
}
