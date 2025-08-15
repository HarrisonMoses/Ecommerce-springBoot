package com.harrisonmoses.store.services.payment;

import com.harrisonmoses.store.Dtos.CheckoutResponse;
import com.harrisonmoses.store.Entity.Order;

public interface PaymentGateway {
    CheckoutSession checkoutsession (Order order);
}
