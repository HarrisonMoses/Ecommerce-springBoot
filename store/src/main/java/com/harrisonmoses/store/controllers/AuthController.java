package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.JwtResponse;
import com.harrisonmoses.store.Dtos.LoginRequest;
import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Mappers.UserMapper;
import com.harrisonmoses.store.repositories.UserRepository;
import com.harrisonmoses.store.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );

       var  user = userRepository.findByEmail(request.getEmail()).orElseThrow();

       var token = jwtService.generateToken(user);
       return ResponseEntity.ok(new  JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id =  (Long) authentication.getPrincipal();

        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var mappedUser = userMapper.toDto(user);
        return ResponseEntity.ok(mappedUser);
    }

    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String header){
        System.out.println("validate called");
        var token = header.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> unAuthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
