package com.harrisonmoses.store.controllers;


import com.harrisonmoses.store.Entity.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController {

    @RequestMapping("/greet")
    public Message greeting(){
        return new Message("Hello World");
    }
}
