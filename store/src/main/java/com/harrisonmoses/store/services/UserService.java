package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Entity.User;
import com.harrisonmoses.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public void addUser(){
        var user = User.builder()
                .email("mary@gmail.com")
                .name("Magumba")
                .password("12345")
                .build();

        userRepository.save(user);

    }

    public void getUser(){
        userRepository.findById(2L).orElseThrow();
    }

    public void deleteRelated(){
        userRepository.deleteById(1L);
    }

}
