package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.configuration.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;


    public Jwt generateAccessToken(User user){

        return generateToken(user,jwtConfig.getAccessTokenExpiration());

    }

    public Jwt generateRefreshToken(User user){

        return generateToken(user,jwtConfig.getRefreshTokenExpiration());

    }



    private Jwt generateToken(User user, int EXPIRATION_DURATION){
        var claims =  Jwts.claims()
                .setSubject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_DURATION))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());

    }



    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }


}
