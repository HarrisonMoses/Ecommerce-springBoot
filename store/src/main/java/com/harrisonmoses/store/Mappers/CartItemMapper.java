package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Dtos.ProductInfoDto;
import com.harrisonmoses.store.Entity.Cartitem;
import com.harrisonmoses.store.Entity.Product;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "totalPrice", expression = "java(calculateTotal(cartItem))")
    CartItemDto toDto(Cartitem cartItem);

    @Mapping(target = "price", source = "price")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductInfoDto map(Product product);

    default BigDecimal calculateTotal(Cartitem item) {
        return item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }

}
