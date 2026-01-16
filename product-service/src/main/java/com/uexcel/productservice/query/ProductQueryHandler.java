package com.uexcel.productservice.query;

import com.uexcel.productservice.core.data.ProductEntity;
import com.uexcel.productservice.core.data.ProductRepository;
import com.uexcel.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryHandler {
    private final ProductRepository productRepository;
    public ProductQueryHandler(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> getProductList(FindProductQuery findProductQuery) {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductRestModel> productRestModelList = new ArrayList<>();
        productEntityList.forEach(productEntity -> {
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity, productRestModel);
            productRestModelList.add(productRestModel);
        });
        return productRestModelList;
    }
}
