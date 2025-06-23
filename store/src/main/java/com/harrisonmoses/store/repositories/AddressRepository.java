package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
  }