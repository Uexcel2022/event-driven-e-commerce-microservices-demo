package com.uexcel.orderservice.query;

import com.uexcel.orderservice.core.event.OrderApprovedEvent;
import com.uexcel.orderservice.core.event.OrderCreatedEvent;
import com.uexcel.orderservice.core.data.OrderEntity;
import com.uexcel.orderservice.core.data.OrderRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class OrderEventsHandler {
    private final static Logger logger = LoggerFactory.getLogger(OrderEventsHandler.class);
    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void OnOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, orderEntity);
        orderRepository.save(orderEntity);
        logger.info("OrderCreatedEvent {}", orderCreatedEvent);

    }
    @EventHandler
    public void handle(OrderApprovedEvent orderApprovedEvent)
    {
        OrderEntity  order =
                orderRepository.findByOrderId(orderApprovedEvent.getOrderId());
        if(order==null){
            throw new IllegalStateException("Order Not Found!");
        }
        order.setOrderStatus(orderApprovedEvent.getOrderStatus());
        orderRepository.save(order);
        logger.info("OrderApprovedEvent; order approved {}", orderApprovedEvent);
    }
}
