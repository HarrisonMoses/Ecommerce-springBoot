package com.harrisonmoses.store.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class GetCartItemRequest {
    private Long id;
    private String name;
    private BigDecimal price;
}
