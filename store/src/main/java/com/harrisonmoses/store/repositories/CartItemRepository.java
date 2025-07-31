package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<Cartitem,Long> {


}
