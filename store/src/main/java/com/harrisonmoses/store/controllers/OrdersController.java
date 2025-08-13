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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> orderInstance(@PathVariable long orderId){

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));

    }

}
