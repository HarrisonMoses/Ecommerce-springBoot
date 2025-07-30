package com.harrisonmoses.store.Dtos;

import com.harrisonmoses.store.Entity.Product;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItemDto {
    private ProductInfoDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
