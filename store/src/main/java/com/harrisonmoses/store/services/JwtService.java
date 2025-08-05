package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import io.jsonwebtoken.Claims;
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

    public String generateAccessToken(User user){
        var EXPIRATION = 5*3600;
        return generateToken(user,EXPIRATION);

    }

    public String generateRefreshToken(User user){
        var EXPIRATION = 7*24*60*60;
        return generateToken(user,EXPIRATION);

    }



    private String generateToken(User user, int EXPIRATION_DURATION){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_DURATION))
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
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
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public Long getIdFromToken(String token){

        return Long.valueOf(getClaims(token).getSubject());
    }
}
