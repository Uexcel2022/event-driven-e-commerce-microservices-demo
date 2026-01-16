package com.uexcel.productservice.query.rest;

import com.uexcel.productservice.query.FindProductQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping(value = "/products", produces=MediaType.APPLICATION_JSON_VALUE)
public class ProductQueryController {
    final QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
  public List<ProductRestModel> findProducts(){
        FindProductQuery findProductQuery = new FindProductQuery();
     return queryGateway.query(findProductQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
   }
}
