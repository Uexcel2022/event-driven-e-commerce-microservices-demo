package com.uexcel.productservice.command.rest;
import com.uexcel.productservice.command.CreateProductCommand;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/products",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCommandController {
    private final Environment env;
    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseStatusException createProduct(@Valid @RequestBody CreateProductModel model) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .title(model.getTitle())
                .build();
        try {
            return commandGateway.sendAndWait(createProductCommand);
        } catch (Exception ex) {
            return new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
                    );
        }
    }
}


