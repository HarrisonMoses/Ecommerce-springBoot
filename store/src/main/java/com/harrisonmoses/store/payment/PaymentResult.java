package com.harrisonmoses.store.payment;

import com.harrisonmoses.store.Entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResult {
    long order_id;
    PaymentStatus orderStatus;
}
