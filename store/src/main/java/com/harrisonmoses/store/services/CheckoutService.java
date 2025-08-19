package com.harrisonmoses.store.services;


import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.CheckoutResponse;
import com.harrisonmoses.store.Entity.*;
import com.harrisonmoses.store.Exceptions.CartIsEmptyException;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Exceptions.PaymentException;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.harrisonmoses.store.services.payment.PaymentGateway;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class  CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartMapper cartMapper;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkoutOrder(CheckOutRequest request) {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null) {
            System.out.println("Cart not found");
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()){
            System.out.println("Cart is empty");
            throw new CartIsEmptyException();
        }
        System.out.println("passed");
        var order = getOrder(cart,authService.getCurrentUser());
        System.out.println("pass two");
        orderRepository.save(order);

        cart.clear();

        try{
            var session = paymentGateway.checkoutsession(order);
            return new CheckoutResponse(order.getId(), session.getSessionUrl());

        }catch(PaymentException e){
            orderRepository.delete(order);
            throw e;

        }



    }


    private Order getOrder(Cart cart, User user) {
        //create an order object
        var order = new Order();
        order.setCreatedAt(java.time.LocalDate.now());
        order.setStatus(Status.PENDING);
        order.setCustomer(user);
        //get the total price of the cart to set the order_total price
        var cartDto = cartMapper.createCart(cart);
        order.setTotalPrice(cartDto.getTotalPrice());


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
