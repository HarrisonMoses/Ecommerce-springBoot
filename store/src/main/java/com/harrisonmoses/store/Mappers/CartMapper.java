package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.AddCartItemRequest;
import com.harrisonmoses.store.Dtos.CartDto;
import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Entity.Cart;
import com.harrisonmoses.store.Entity.Cartitem;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target ="dateCreated" ,expression ="java(java.time.LocalDate.now())" )
    @Mapping(target = "totalPrice", expression = "java(getTotalPrice(cart.getCartItems()))")
    CartDto createCart(Cart cart);

    default BigDecimal getTotalPrice(Set<Cartitem> cartItemDtoList){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Cartitem item : cartItemDtoList) {
            BigDecimal calculatedTotalPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(calculatedTotalPrice);
        }
        return totalPrice;
    };

}
