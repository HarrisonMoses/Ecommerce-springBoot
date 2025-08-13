package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.OrderDto;
import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.OrderItem;
import com.harrisonmoses.store.Entity.Status;
import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Exceptions.OrderNotFoundException;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.Mappers.OrderMapper;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class OrderService {
    public final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    private User getCurrentUser(){
        var userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userId).orElseThrow();
    }

    public Order checkoutOrder(CheckOutRequest request) {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        //create an order object
        var order = new Order();
        order.setCreatedAt(java.time.LocalDate.now());
        order.setStatus(Status.PENDING);
        order.setCustomer(getCurrentUser());
        //get the total price of the cart to set the order_total price
        var cartDto = cartMapper.createCart(cart);
        order.setTotalPrice(cartDto.getTotalPrice());

        //
        cart.getCartItems().forEach(cartItem -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
        });
        return order;

    }

    public List<OrderDto> getOrders(){
        //get the current user.
        var user = getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();

    }

    public OrderDto getOrder(long orderId){
        var user = getCurrentUser();
        var order = orderRepository.getAllByCustomer(user)
                .stream().filter(o -> o.getId() == orderId)
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);

       return orderMapper.toDto(order);

    }
}
