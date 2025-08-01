package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.LoginRequest;
import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Mappers.UserMapper;
import com.harrisonmoses.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request){
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().build();
        }
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
}
