package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Mappers.UserMapper;
import com.harrisonmoses.store.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("")
    public Iterable<UserDto> getUsers(
           @RequestParam( required= false, defaultValue = "", name ="sort") String sort
    ) {
        if(!Set.of("name", "email").contains(sort)){
            sort = "name";
        }

       return userRepository.findAll(Sort.by(sort))
               .stream()
               .map(userMapper::toDto)
               .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

