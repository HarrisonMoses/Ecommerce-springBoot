package com.harrisonmoses.store.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cartegory {

    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "product")
    private Set<Product> product  = new HashSet<>();


}
