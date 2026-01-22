package com.uexcel.orderservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
}
