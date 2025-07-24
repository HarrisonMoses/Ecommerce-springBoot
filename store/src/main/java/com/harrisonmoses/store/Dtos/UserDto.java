package com.harrisonmoses.store.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    Long id;
    String Email;
    String Name;
}
