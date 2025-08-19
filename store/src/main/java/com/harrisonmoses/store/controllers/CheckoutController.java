package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.CheckoutResponse;
import com.harrisonmoses.store.Exceptions.CartIsEmptyException;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.CheckoutService;
import com.harrisonmoses.store.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @Value("stripe.webhookSecreteKey")
    private String endpointSecret;



    @PostMapping
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request){
//        try {
           return ResponseEntity.ok(checkoutService.checkoutOrder(request));
//        }catch (Exception ex){
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Some thing went wrong on the server"));
//        }

    }

    @PostMapping("/webhook")
    public void stripeWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
            ) throws SignatureVerificationException {

        var event = Webhook.constructEvent(payload, signature, endpointSecret);
        System.out.println(event.getType());

        var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

        switch (event.getType()) {
            case "payment_intent.succeeded"->{
                //update the order status;

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
