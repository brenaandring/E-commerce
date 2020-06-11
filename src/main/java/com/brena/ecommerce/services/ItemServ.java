package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.repositories.ItemRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServ {
    private final ItemRepo itemRepo;

    public ItemServ(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public List<Item> allItems() {
        return itemRepo.findAll();
    }

    public void saveItem(Item item) {
        itemRepo.save(item);
    }

    public Item findItem(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        return item.orElse(null);
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }
}
