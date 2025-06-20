package com.harrisonmoses.store;

import com.harrisonmoses.store.Entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {

      ApplicationContext context =  SpringApplication.run(StoreApplication.class, args);

    }

}
