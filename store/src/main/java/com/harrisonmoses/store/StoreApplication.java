package com.harrisonmoses.store;

import com.harrisonmoses.store.Entity.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {

//        ApplicationContext context =  SpringApplication.run(StoreApplication.class, args);

        var cartegory = Cartegory.builder()
                .id(1)
                .name("Gaming")
                .build();

        var product = Product.builder()
                .id(2)
                .name("BMW")
                .price(100)
                .build();

        product.setCartegory(cartegory);
        System.out.println(product);



    }

}
