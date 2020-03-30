package com.brena.ecommerce.services;

import com.brena.ecommerce.repositories.ItemRepo;
import org.springframework.stereotype.Service;

@Service
public class ItemServ {
    private final ItemRepo itemRepo;

    public ItemServ(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }
}
