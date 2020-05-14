package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServ {
    private final OrderRepo orderRepo;

    public OrderServ(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public Order findByUser(User user) {
        return orderRepo.findByUser(user);
    }

    public List<Order> allOrders() {
        return orderRepo.findAll();
    }
}
