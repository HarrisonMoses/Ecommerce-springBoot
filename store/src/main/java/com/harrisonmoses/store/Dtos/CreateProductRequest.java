package com.harrisonmoses.store.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private Long categoryId;
}
