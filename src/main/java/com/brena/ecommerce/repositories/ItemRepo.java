package com.brena.ecommerce.repositories;

import com.brena.ecommerce.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findAll();

    Page<Item> findAllItemsByCategoryId(Long id, Pageable pageable);

    Optional<Item> findById(Long id);
}
