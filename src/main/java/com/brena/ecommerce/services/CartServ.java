package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.repositories.ItemRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServ {
    private final ItemRepo itemRepo;

    private final Map<Item, Integer> items = new HashMap<>();

    public CartServ(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public void addItem(Item item) {
        if (items.containsKey(item)) {
            items.replace(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
    }

    public void removeItem(Item item) {
        if (items.get(item) > 1) {
            items.replace(item, items.get(item) - 1);
        } else if (items.get(item) == 1) {
            items.remove(item);
        }
    }

    public Map<Item, Integer> getItemsInCart() {
        return Collections.unmodifiableMap(items);
    }

    public void checkout(Order order) {
        Item item;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Optional<Item> byId = itemRepo.findById(entry.getKey().getId());
            if (!byId.isPresent()) {
                throw new IllegalStateException("Item is not available");
            }
            item = byId.get();
            ;
            if (item.getQuantity() < entry.getValue()) {
                throw new IllegalStateException("Item is not available");
            }
            item.setQuantity(item.getQuantity() - entry.getValue());
            itemRepo.save(item);
        }
        itemRepo.flush();
        items.clear();
    }

    public BigDecimal getTotal() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
