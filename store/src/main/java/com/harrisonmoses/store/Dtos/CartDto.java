package com.harrisonmoses.store.Dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;

    @JsonProperty("items")
    private Iterable<CartItemDto> Items;

    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate dateCreated;

    private BigDecimal totalPrice;

}
