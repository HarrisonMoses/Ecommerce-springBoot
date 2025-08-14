package com.harrisonmoses.store.services;


import com.harrisonmoses.store.Dtos.CheckOutRequest;
import com.harrisonmoses.store.Dtos.CheckoutResponse;
import com.harrisonmoses.store.Entity.*;
import com.harrisonmoses.store.Exceptions.CartIsEmptyException;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartMapper cartMapper;
    private final AuthService authService;

    @Value("${websiteUrl}")
    private String websiteDomain;


    @Transactional
    public CheckoutResponse checkoutOrder(CheckOutRequest request) throws StripeException {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()){
            throw new CartIsEmptyException();
        }

        var order = getOrder(cart,authService.getCurrentUser());
        orderRepository.save(order);

        try{

            //create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteDomain + "/checkout-success?="+order.getId())
                    .setCancelUrl(websiteDomain + "/checkout-cancel");

            order.getItems().forEach(item -> {
                        var lineItem = SessionCreateParams.LineItem.builder()
                                .setQuantity(Long.valueOf(item.getQuantity()))
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                 .setCurrency("usd")
                                                .setUnitAmountDecimal(
                                                        item.getUnitPrice()
                                                        .multiply(BigDecimal.valueOf(100))
                                                )
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(item.getProduct().getName())
                                                                .build()
                                                ).build()

                                ).build();

                        builder.addLineItem(lineItem);
                    }
            );

            var session = Session.create(builder.build());


            return new CheckoutResponse(order.getId(), session.getUrl());

        }catch(Exception ex){
            orderRepository.delete(order);
            throw ex;

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
