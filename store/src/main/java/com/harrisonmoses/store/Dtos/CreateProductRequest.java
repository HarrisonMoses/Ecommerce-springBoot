package com.harrisonmoses.store.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotBlank(message = "name is required.")
    private String name;
    @NotNull(message = "price is required")
    private BigDecimal price;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "category is required")
    private Long categoryId;
}
