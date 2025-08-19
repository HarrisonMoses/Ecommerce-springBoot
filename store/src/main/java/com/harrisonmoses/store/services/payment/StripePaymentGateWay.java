package com.harrisonmoses.store.services.payment;


import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.OrderItem;
import com.harrisonmoses.store.Exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class StripePaymentGateWay implements PaymentGateway {

    //private final OrderRepository orderRepository;

    @Value("${websiteUrl}")
    private String websiteDomain;

    @Override
    public CheckoutSession checkoutsession(Order order) {
        try{
            //create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteDomain + "/checkout-success?="+order.getId())
                    .setCancelUrl(websiteDomain + "/checkout-cancel");

            order.getItems().forEach(item -> {
                        var lineItem = createLineItem(item);
                        builder.addLineItem(lineItem);
                    }
            );

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());

        }catch(StripeException ex){
            System.out.println(ex.getMessage()); // log the exception.
            throw new PaymentException();

        }
    }

    private  SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private  SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(100))
                )
                .setProductData(createProductData(item)
                ).build();
    }

    private  SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
