package com.harrisonmoses.store.payment;

import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.services.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
   CheckoutSession checkoutsession (Order order);

   Optional<PaymentResult> parseWebhookRequest (WebhookRequest request);
}
