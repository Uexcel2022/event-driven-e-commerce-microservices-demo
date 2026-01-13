package com.uexcel.productservice.query;

import com.uexcel.productservice.core.data.ProductEntity;
import com.uexcel.productservice.core.data.ProductRepository;
import com.uexcel.productservice.core.event.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductEventHandler {
    private final ProductRepository productRepository;
    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productRepository.save(productEntity);
    }
}
