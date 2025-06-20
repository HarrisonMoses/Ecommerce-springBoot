package com.harrisonmoses.store;

import com.harrisonmoses.store.Entity.Profile;
import com.harrisonmoses.store.Entity.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

//        ApplicationContext context =  SpringApplication.run(StoreApplication.class, args);

        var user =  User.builder()
                .name("Harry")
                .password("1234")
                .email("harris@gmail.com")
                .build();

        var profile = Profile.builder()
                .bio("Harry Potter")
                .loyaltyPoints(5)
                .phoneNumber("123456789")
                .build();

       user.addProfile(profile);
        System.out.println(user);



    }

}
