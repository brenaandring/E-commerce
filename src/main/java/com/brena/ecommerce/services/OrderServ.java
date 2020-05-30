package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServ {
    private final OrderRepo orderRepo;

    public OrderServ(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void saveNewOrder(Order order) {
        order.setStatus("New");
        orderRepo.save(order);
    }

    public void saveShippedOrder(Order order) {
        order.setStatus("Shipped");
        orderRepo.save(order);
    }

    public void saveCancelledOrder(Order order) {
        order.setStatus("Cancelled");
        orderRepo.save(order);
    }

    public Order findByUser(User user) {
        return orderRepo.findByUser(user);
    }

    public List<Order> allOrders() {
        return orderRepo.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }
}
