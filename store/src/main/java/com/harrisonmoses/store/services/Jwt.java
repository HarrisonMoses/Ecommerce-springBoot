package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class Jwt {
    private Claims claims;
    private SecretKey secretKey;


    public Jwt(Claims claims,  SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public Claims getClaims() {
        return claims;
    }

    public Long getIdFromToken(){
        return Long.valueOf(claims.getSubject());
    }

    public boolean IsValid(){
        try{
            return !claims.getExpiration().after(new Date());

        }catch(JwtException ex){
            return true;
        }
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString(){
            return Jwts.builder().claims(claims).signWith(secretKey).compact();

    }

}
