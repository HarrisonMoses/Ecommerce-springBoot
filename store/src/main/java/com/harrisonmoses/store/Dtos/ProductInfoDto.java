package com.harrisonmoses.store.Dtos;

import lombok.Data;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Data
public class ProductInfoDto {
    private long id;
    private String name;
    private BigDecimal price;
}
