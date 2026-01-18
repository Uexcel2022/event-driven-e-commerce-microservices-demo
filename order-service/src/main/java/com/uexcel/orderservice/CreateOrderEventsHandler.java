package com.uexcel.orderservice;

import com.uexcel.orderservice.core.event.OrderCreatedEvent;
import com.uexcel.orderservice.core.data.OrderEntity;
import com.uexcel.orderservice.core.data.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderEventsHandler {
    private final OrderRepository orderRepository;

    public CreateOrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void OnOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, orderEntity);
        orderRepository.save(orderEntity);
    }
}
