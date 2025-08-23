package com.harrisonmoses.store.controllers;


import com.harrisonmoses.store.Dtos.OrderDto;
import com.harrisonmoses.store.Exceptions.OrderNotFoundException;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.OrderService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        var orders = orderService.getOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> orderInstance(@PathVariable long orderId){

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Map<String,String>> OrderNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Order Not Found"));
    }


}
