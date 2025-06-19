package com.harrisonmoses.store;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "id")
    private Long  id;

    @Column(name= "name")
    private String name;

    @Column(name="email", unique= true)
    private String email;

    @Column(name= "password")
    private String password;

}
