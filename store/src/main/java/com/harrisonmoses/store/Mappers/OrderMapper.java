package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.OrderDto;
import com.harrisonmoses.store.Entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);

}
