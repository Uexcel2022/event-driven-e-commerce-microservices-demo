package com.uexcel.productservice.command.interceptor;

import com.uexcel.productservice.command.CreateProductCommand;
import com.uexcel.productservice.core.data.ProductLookupRepository;
import com.uexcel.productservice.core.errorhandling.ProductAlreadyExistException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @Override
    public @NonNull BiFunction<Integer, CommandMessage<?>, CommandMessage<?>>
    handle(@NonNull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
           if(command.getPayload() instanceof CreateProductCommand cpc){
               if(productLookupRepository.existsByProductIdOrTitle(cpc.getProductId(),cpc.getTitle())){
                   throw new IllegalStateException("""
                           Product with ID: %s or Title: %s already exists."""
                           .formatted(cpc.getProductId(),cpc.getTitle()));
               }
            }
            return command;
        };
    }
}
