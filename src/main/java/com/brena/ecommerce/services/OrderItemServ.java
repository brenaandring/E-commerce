package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.OrderItem;
import com.brena.ecommerce.repositories.OrderItemRepo;
import com.brena.ecommerce.repositories.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemServ {
    private final OrderItemRepo orderItemRepo;

    public OrderItemServ(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    public OrderItem findById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepo.findById(id);
        return orderItem.orElse(null);
    }
}
