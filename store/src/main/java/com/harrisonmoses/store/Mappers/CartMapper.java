package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.CartDto;
import com.harrisonmoses.store.Entity.Cart;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target ="dateCreated" ,expression ="java(java.time.LocalDate.now())" )
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto createCart(Cart cart);


}
