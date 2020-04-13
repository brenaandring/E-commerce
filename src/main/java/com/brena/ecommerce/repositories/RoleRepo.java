package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
    List<Role> findAll();

    List<Role> findByName(String name);
}
