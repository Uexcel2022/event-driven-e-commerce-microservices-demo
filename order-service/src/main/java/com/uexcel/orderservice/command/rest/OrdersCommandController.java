package com.uexcel.orderservice.command.rest;

import com.uexcel.orderservice.command.CreateOrderCommand;
import com.uexcel.orderservice.command.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {
    private final CommandGateway commandGateway;

    public OrdersCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderModel createOrderModel){
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .orderStatus(OrderStatus.CREATED)
                .quantity(createOrderModel.getQuantity())
                .productId(createOrderModel.getProductId())
                .addressId(createOrderModel.getAddressId())
                .build();

        return commandGateway.sendAndWait(createOrderCommand);
    }
}
