package com.uexcel.productservice.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.jspecify.annotations.NonNull;

public class ProductServiceEventErrorHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@NonNull Exception exception, @NonNull EventMessage<?> event,
                        @NonNull EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }
}
