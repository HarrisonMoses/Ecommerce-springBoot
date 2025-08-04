package com.harrisonmoses.store.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email){
        var EXPIRATION = 86400;
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public boolean validateToken(String token){
        try{
            var claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
            return claims.getExpiration().after(new Date());

        }catch(JwtException ex){
            return false;
        }
    }
}
