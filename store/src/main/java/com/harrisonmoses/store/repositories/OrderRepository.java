package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.Order;
import com.harrisonmoses.store.Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  OrderRepository extends JpaRepository<Order,Long> {

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o where o.customer = :customer")
    List<Order> getAllByCustomer(@Param("customer") User customer);
}
