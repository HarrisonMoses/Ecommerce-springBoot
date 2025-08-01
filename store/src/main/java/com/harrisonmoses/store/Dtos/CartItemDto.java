package com.harrisonmoses.store.Dtos;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItemDto {
    private ProductInfoDto product;
    private int quantity;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
