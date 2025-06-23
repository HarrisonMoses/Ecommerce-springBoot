package com.harrisonmoses.store.repositories;

import com.harrisonmoses.store.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
