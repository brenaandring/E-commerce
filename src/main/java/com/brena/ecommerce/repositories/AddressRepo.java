package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Address;
import com.brena.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends CrudRepository<Address, Long> {
    List<Address> findAll();

    Address findByUser(User user);
}
