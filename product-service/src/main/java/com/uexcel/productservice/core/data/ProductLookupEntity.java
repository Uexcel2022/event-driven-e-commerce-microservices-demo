package com.uexcel.productservice.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "productlookup")
public class ProductLookupEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String productId;
    @Column(nullable = false, unique = true)
    private String title;
}
