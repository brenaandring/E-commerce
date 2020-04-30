package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends CrudRepository<Review, Long> {
    List<Review> findAll();
}
