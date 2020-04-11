package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServ {
    private ItemRepo itemRepo;

    public ItemServ(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    // retrieve all items
    public List<Item> allItems() {
        return itemRepo.findAll();
    }

    // create/update an item
    public void saveItem(Item item) {
        itemRepo.save(item);
    }

    // find an item
    public Item findItem(Long id) {
        Optional<Item> item = itemRepo.findById(id);
        return item.orElse(null);
    }

    // delete an item
    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }
}
