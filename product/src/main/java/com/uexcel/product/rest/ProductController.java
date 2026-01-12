package com.uexcel.product.rest;
import com.uexcel.product.command.CreateProductCommand;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/products",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final Environment env;
    private final CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@RequestBody CreateProductModel model) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .title(model.getTitle())
                .build();
        try {
            return commandGateway.sendAndWait(createProductCommand);
        }catch (Exception ex){
            return ex.getLocalizedMessage();
        }
    }

    @GetMapping
    public String getProduct() {
        return "Product fetched :"+ env.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "Product updated";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Product deleted";
    }
}

