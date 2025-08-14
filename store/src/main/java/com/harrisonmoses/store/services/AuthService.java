package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.geom.QuadCurve2D;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        var userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userId).orElseThrow();
    }
}
