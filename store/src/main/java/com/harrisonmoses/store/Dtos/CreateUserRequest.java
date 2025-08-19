package com.harrisonmoses.store.Dtos;

import com.harrisonmoses.store.Entity.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "name should be provided.")
    private String name;

    @NotBlank(message = "validEmil should be provide.")
    private String email;

    @NotBlank(message = "Password can't be null")
    @Size(max =12, min = 6, message = "password should be 6 or more characters.")
    private String password;

    private Role role = Role.USER;

}
