package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {


}
