package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//  NOT USED
@Repository
public interface PhotoRepo extends CrudRepository<Photo, Long> {
    Optional<Photo> findById(Long id);

    Photo findByItem(Item item);

    List<Photo> findAll();
}
