package com.harrisonmoses.store.payment;


import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.OrderItem;
import com.harrisonmoses.store.Entity.PaymentStatus;
import com.harrisonmoses.store.Exceptions.PaymentException;
import com.harrisonmoses.store.services.WebhookRequest;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StripePaymentGateWay implements PaymentGateway {

    //private final OrderRepository orderRepository;
    @Value("${stripe.webhook_key}")
    private String webhook_key;

    @Value("${websiteUrl}")
    private String websiteDomain;

    @Override
    public CheckoutSession checkoutsession(Order order) {
        try{
            //create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteDomain + "/checkout-success?="+order.getId())
                    .setCancelUrl(websiteDomain + "/checkout-cancel")
                            .putMetadata("order_id",order.getId().toString());

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

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getSignature().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhook_key);

            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));
                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));
                default -> Optional.empty();
            };

        } catch (SignatureVerificationException ex) {
            throw new PaymentException("Invalid Signature");
        }
    }



    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not Deserialize event")
        );

        var paymentIntent = (PaymentIntent) stripeObject;
        var orderIdStr = paymentIntent.getMetadata().get("order_id");

        if (orderIdStr == null || !orderIdStr.matches("\\d+")) {
            throw new PaymentException("Invalid order_id in Stripe metadata: " + orderIdStr);
        }

        return Long.valueOf(orderIdStr);
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
