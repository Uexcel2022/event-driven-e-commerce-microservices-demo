package com.uexcel.userservice.query;

import com.uexcel.core.model.PaymentDetails;
import com.uexcel.core.model.Users;
import com.uexcel.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserEventsHandler {

    @QueryHandler
    public Users on(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123card")
                .cvv("123")
                .name("SERGY RARGOPOLOV")
                .validUntilMonth(12)
                .getValidUntilYear(2030)
                .build();
        return Users.builder()
                .firstName("Sergey")
                .lastName("Rargopolov")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
    }

}
