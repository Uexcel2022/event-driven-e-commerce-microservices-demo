package com.uexcel.productservice.command;


import com.uexcel.productservice.core.data.ProductLookupEntity;
import com.uexcel.productservice.core.data.ProductLookupRepository;
import com.uexcel.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEventHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductLookupEntity  productLookupEntity = new ProductLookupEntity();
        BeanUtils.copyProperties(event,productLookupEntity);
        productLookupRepository.save(productLookupEntity);
    }
}
