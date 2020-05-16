package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, Long> {
    Optional<OrderItem> findById(Long id);
}
