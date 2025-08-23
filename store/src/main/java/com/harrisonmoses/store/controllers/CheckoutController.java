package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Exceptions.CartIsEmptyException;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.CheckoutService;
import com.harrisonmoses.store.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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



    @PostMapping
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request){

        return ResponseEntity.ok(checkoutService.checkoutOrder(request));


    }

    @PostMapping("/webhook")
    public void stripeWebhook(
            @RequestHeader Map<String,String> signature,
            @RequestBody String payload
    )  {
        checkoutService.handleWebhookRequest(new WebhookRequest(payload,signature));
    }


@ExceptionHandler(CartIsEmptyException.class)
private ResponseEntity<?> cartIsEmptyException(){
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Cart is empty"));
}
}
