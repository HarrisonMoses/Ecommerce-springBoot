package com.harrisonmoses.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name="id")
    private Long id;

    @Column(name="street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;
}
