package com.uexcel.paymentservice.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @Column(nullable = false)
    private String paymentId;
    @Column(nullable = false)
    public String orderId;
}
