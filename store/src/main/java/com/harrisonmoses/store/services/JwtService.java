package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.configuration.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;


    public String generateAccessToken(User user){

        return generateToken(user,jwtConfig.getAccessTokenExpiration());

    }

    public String generateRefreshToken(User user){

        return generateToken(user,jwtConfig.getRefreshTokenExpiration());

    }



    private String generateToken(User user, int EXPIRATION_DURATION){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_DURATION))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token){
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());

        }catch(JwtException ex){
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public Long getIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    public String getRoleFromToken(String token){
        return String.valueOf(getClaims(token).get("role"));
    }
}
