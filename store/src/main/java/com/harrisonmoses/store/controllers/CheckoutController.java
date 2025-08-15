package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.CheckoutResponse;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.CheckoutService;
import com.harrisonmoses.store.services.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("checkout")
public class CheckoutController {

    private final OrderRepository orderRepository;
    private final CheckoutService checkoutService;



    @PostMapping
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request){
        try {
           return ResponseEntity.ok(checkoutService.checkoutOrder(request));
        }catch (Exception ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Some thing went wrong on the server"));
        }


    }
}
