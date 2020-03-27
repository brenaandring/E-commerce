package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
}