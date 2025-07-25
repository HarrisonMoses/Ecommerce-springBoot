package com.harrisonmoses.store.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harrisonmoses.store.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Long categoryId;

}
