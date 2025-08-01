package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Dtos.ProductInfoDto;
import com.harrisonmoses.store.Entity.CartItem;
import com.harrisonmoses.store.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "price", source = "price")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductInfoDto map(Product product);

}
