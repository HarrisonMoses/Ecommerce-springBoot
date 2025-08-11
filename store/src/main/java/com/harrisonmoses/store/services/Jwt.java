package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;


public class Jwt {
    private final Claims claims;
    private final SecretKey secret;


    public Jwt(Claims claims,  SecretKey secret) {
        this.claims = claims;
        this.secret = secret;
    }

    public SecretKey getSecret() {
        return secret;
    }

    public Claims getClaims() {
        return claims;
    }

    public Long getId(){
        return Long.valueOf(claims.getSubject());
    }

    public boolean isValid(){
        try{
            return !claims.getExpiration().before(new Date());
        }catch(JwtException ex){
            return false;
        }
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString(){
            return Jwts.builder().claims(claims).signWith(secret).compact();

    }

}
