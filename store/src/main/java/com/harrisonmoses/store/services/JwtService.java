package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.configuration.JwtConfig;
import io.jsonwebtoken.Claims;
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
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * EXPIRATION_DURATION))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token){
        try{
            var claims = getClaims(token);

            return  new Jwt(claims, jwtConfig.getSecretKey());
        }catch(Exception ex){
            return null;
        }

    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }


}
