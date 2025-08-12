package com.harrisonmoses.store.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private ProductInfoDto product;
    private int quantity;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
