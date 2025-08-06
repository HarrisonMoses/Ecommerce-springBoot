package com.harrisonmoses.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth/admin")
class AdminController {

    @GetMapping("")
    public String sayHello(){
        return "Hello admin";
    }

}