package com.harrisonmoses.store.Dtos;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;


}
