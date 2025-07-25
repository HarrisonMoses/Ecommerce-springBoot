package com.harrisonmoses.store.Dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
