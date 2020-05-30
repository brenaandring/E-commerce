package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {
    List<Order> findAll();

    Order findByUser(User user);

    Optional<Order> findById(Long id);

    List<Order> findByStatus(String status);
}
