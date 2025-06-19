package com.harrisonmoses.store;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "address")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id")
    private Long id;

    @Column(name="street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name="zip")
    private String zip;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
