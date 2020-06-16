package com.brena.ecommerce.services;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.repositories.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServ {

    public static final String STATUS_NEW = "New";
    public static final String STATUS_SHIPPED = "Shipped";
    public static final String STATUS_CANCELLED = "Cancelled";

    private final OrderRepo orderRepo;
    private final CartServ cartServ;

    public OrderServ(OrderRepo orderRepo, CartServ cartServ) {
        this.orderRepo = orderRepo;
        this.cartServ = cartServ;
    }

    public void saveNewOrder(Order order, Address address, User user, BigDecimal total) {
        order.setUser(user);
        order.setAddress(address);
        order.setTotal(total);
        order.setStatus(STATUS_NEW);
        orderRepo.save(order);
    }

    public void saveShippedOrder(Long id) {
        Optional<Order> orderOp = orderRepo.findById(id);
        if (orderOp.isPresent()) {
            Order order = orderOp.get();
            order.setStatus(STATUS_SHIPPED);
            orderRepo.save(order);
        }
    }

    public void saveCancelledOrder(Long id) {
        Optional<Order> orderOp = orderRepo.findById(id);
        if (orderOp.isPresent()) {
            Order order = orderOp.get();
            order.setStatus(STATUS_CANCELLED);
            orderRepo.save(order);
        }
    }

    public void confirmItems(Order order) {
        Map<Item, Integer> itemsInCart = cartServ.getItemsInCart();
        for (Map.Entry<Item, Integer> entry : itemsInCart.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(entry.getKey());
            orderItem.setCount(entry.getValue());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
    }

    public Page<Order> allOrders(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }
}
