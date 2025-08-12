package com.harrisonmoses.store.Dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull
    private UUID cartId;
}
