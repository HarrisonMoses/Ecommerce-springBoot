package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harrisonmoses.store.Entity.User;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("")
    public Iterable<UserDto> getUsers() {
       return userRepository.findAll().stream()
               .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
               .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}

