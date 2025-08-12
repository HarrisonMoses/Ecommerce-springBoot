package com.harrisonmoses.store.controllers;


import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.OrderDto;
import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.Status;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.repositories.UserRepository;
import com.harrisonmoses.store.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class OrdersController {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final OrderRepository orderRepository;
    private final OrderService orderService;




    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOutRequest request) {
        var order = orderService.checkoutOrder(request);
        //save the order
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("orderId: ", order.getId()));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }

    @GetMapping("/orders/{orderId}")
    public void orderInstance(){

    }

}
