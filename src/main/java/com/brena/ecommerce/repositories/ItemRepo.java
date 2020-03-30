package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends CrudRepository<Item, Long> {
    List<Item> findAll();
}
