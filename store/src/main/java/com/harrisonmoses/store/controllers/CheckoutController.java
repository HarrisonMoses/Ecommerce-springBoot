package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Entity.Status;
import com.harrisonmoses.store.Exceptions.CartIsEmptyException;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("checkout")
public class CheckoutController {

    private final OrderRepository orderRepository;
    private final CheckoutService checkoutService;

    @Value("${stripe.webhook_key}")
    private String webhook_key;



    @PostMapping
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request){

           return ResponseEntity.ok(checkoutService.checkoutOrder(request));


    }

    @PostMapping("/webhook")
    public void stripeWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
            ) throws SignatureVerificationException {
        System.out.println(webhook_key);
        var event = Webhook.constructEvent(payload, signature, webhook_key);
        System.out.println(event.getType());

        var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

        switch (event.getType()) {
            case "payment_intent.succeeded"->{
                //update the order status;
                var paymentIntent = (PaymentIntent)stripeObject;
                if(paymentIntent != null){
                    var orderId = paymentIntent.getMetadata().get("order_id");
                    var order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                    order.setStatus(Status.PAID);
                    orderRepository.save(order);
                }

            }
            case"payment_intent.failed"->{
                //od something else;
            }

        }

    }
    @ExceptionHandler(CartIsEmptyException.class)
    private ResponseEntity<?> cartIsEmptyException(){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Cart is empty"));
    }
}
