package com.harrisonmoses.store.payment;

import com.harrisonmoses.store.payment.CheckoutSession;
import com.harrisonmoses.store.Entity.Order;

public interface PaymentGateway {
   CheckoutSession checkoutsession (Order order);
}
