package com.harrisonmoses.store.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemRequest    {
    @NotNull
    private long productId;
    private int quantity = 0;
}
