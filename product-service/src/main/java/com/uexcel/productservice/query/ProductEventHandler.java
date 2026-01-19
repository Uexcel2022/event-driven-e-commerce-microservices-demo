package com.uexcel.productservice.query;

import com.uexcel.core.command.event.ProductReservedEvent;
import com.uexcel.productservice.core.data.ProductEntity;
import com.uexcel.productservice.core.data.ProductRepository;
import com.uexcel.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ProcessingGroup("product-group") // only if you have other event handler associated with this event
public class ProductEventHandler {
    private final static Logger logger = LoggerFactory.getLogger(ProductEventHandler.class);
    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @ExceptionHandler(Exception.class)
    public void handleException(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleException(IllegalArgumentException ex) throws Exception {
        // log here
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productRepository.save(productEntity);
    }

    @EventHandler
    public void handle(ProductReservedEvent event){
        ProductEntity productEntity = productRepository.findByProductId(event.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - event.getQuantity());
        productRepository.save(productEntity);
        logger.info("************Product reserved productId {}", event.getProductId());
    }


}
