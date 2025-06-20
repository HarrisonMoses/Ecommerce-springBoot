package com.harrisonmoses.store.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();


    public void addAddress(Address address){
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address){
        addresses.remove(address);
    }

    public void addProfile(Profile profile){
        this.profile = profile;
        profile.setUser(this);
    }

    @OneToOne(mappedBy = "user")
    private Profile profile;


}
