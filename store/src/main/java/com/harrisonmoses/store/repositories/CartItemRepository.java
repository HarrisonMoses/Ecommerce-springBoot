package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.Cart;
import com.harrisonmoses.store.Entity.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<Cartitem,Long> {

    List<Cartitem> findAllByCart(Cart cart);
}
