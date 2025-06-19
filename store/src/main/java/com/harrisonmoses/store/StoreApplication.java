package com.harrisonmoses.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

//        ApplicationContext context =  SpringApplication.run(StoreApplication.class, args);

        var user =  User.builder()
                .name("Harry")
                .password("1234")
                .email("harris@gmail.com")
                .build();

        var address = Address.builder()
                .city("Berlin")
                .state("Paris")
                .zip("12345")
                .state("Paris")
                .build();

       user.addAddress(address);
        System.out.println(user);



    }

}
