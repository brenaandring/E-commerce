package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepo extends CrudRepository<Photo, Long> {
    Optional<Photo> findById(Long id);

    List<Photo> findAll();
}
