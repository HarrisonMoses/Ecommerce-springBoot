package com.harrisonmoses.store;

import com.harrisonmoses.store.Entity.*;
import com.harrisonmoses.store.repositories.UserRepository;
import com.harrisonmoses.store.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {

       SpringApplication.run(StoreApplication.class, args);

//      var repository = context.getBean(UserService.class);
//      repository.deleteRelated();

    }

}
