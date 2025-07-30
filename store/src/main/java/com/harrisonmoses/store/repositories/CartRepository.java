package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CartRepository extends JpaRepository<Cart, UUID>{
}
