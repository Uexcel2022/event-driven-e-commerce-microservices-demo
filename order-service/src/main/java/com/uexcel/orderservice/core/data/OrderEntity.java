package com.uexcel.orderservice.core.data;

import com.uexcel.orderservice.command.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    public  String orderId;
    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
