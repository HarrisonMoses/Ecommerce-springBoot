package com.harrisonmoses.store.services;


import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.OrderItem;
import com.harrisonmoses.store.Entity.Status;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final AuthService authService;

    public Order checkoutOrder(CheckOutRequest request) {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        //create an order object
        var order = new Order();
        order.setCreatedAt(java.time.LocalDate.now());
        order.setStatus(Status.PENDING);
        order.setCustomer(authService.getCurrentUser());
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
}
