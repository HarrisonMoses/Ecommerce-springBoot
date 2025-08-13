package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("checkout")
public class CheckoutController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request) {
        var order = orderService.checkoutOrder(request);
        //save the order
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("orderId: ", order.getId()));
    }
}
