package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Dtos.OrderDto;

import com.harrisonmoses.store.Exceptions.OrderNotFoundException;
import com.harrisonmoses.store.Mappers.OrderMapper;
import com.harrisonmoses.store.repositories.OrderRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> getOrders(){
        //get the current user.
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();

    }

    public OrderDto getOrder(long orderId){
        var user = authService.getCurrentUser();
        var order = orderRepository.getAllByCustomer(user)
                .stream().filter(o -> o.getId() == orderId)
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);

       return orderMapper.toDto(order);

    }
}
