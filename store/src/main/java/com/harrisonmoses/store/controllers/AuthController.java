package com.harrisonmoses.store.controllers;

import com.harrisonmoses.store.Dtos.JwtResponse;
import com.harrisonmoses.store.Dtos.LoginRequest;
import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Mappers.UserMapper;
import com.harrisonmoses.store.configuration.JwtConfig;
import com.harrisonmoses.store.repositories.UserRepository;
import com.harrisonmoses.store.services.Jwt;
import com.harrisonmoses.store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final Jwt jwt;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );

       var  user = userRepository.findByEmail(request.getEmail()).orElseThrow();

       var tokenAccess = jwtService.generateAccessToken(user).toString();
       var refreshToken = jwtService.generateRefreshToken(user).toString();

       var cookie = new Cookie("refreshToken",refreshToken);
       cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
       cookie.setPath("/auth/refresh");
       cookie.setSecure(true);
//       cookie.setHttpOnly(true);

       response.addCookie(cookie);

       return ResponseEntity.ok(new  JwtResponse(tokenAccess));
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue("refreshToken") String refreshToken
    ){
        if(){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId = jwt.getIdFromToken();
        var user = userRepository.findById(userId).orElseThrow();
        var tokenAccess = jwtService.generateAccessToken(user).toString();
        return ResponseEntity.ok(new  JwtResponse(tokenAccess)) ;
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


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> unAuthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
