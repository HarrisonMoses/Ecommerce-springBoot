package com.harrisonmoses.store;


import com.harrisonmoses.store.configuration.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {

       SpringApplication.run(StoreApplication.class, args);
    }

}
