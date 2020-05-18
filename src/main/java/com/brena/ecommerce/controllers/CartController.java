package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class CartController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final CartServ cartServ;
    private final AddressServ addressServ;
    private final OrderServ orderServ;
    private final OrderItemServ orderItemServ;

    public CartController(UserServ userServ,
                          ItemServ itemServ,
                          CartServ cartServ,
                          AddressServ addressServ,
                          OrderServ orderServ,
                          OrderItemServ orderItemServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.cartServ = cartServ;
        this.addressServ = addressServ;
        this.orderServ = orderServ;
        this.orderItemServ = orderItemServ;
    }

    @GetMapping("/user/cart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/cart");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/user/cart/add/{id}")
    public ModelAndView addItemToCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        if (item.getQuantity() > 0) {
            cartServ.addItem(item);
        }
        return shoppingCart();
    }

    @GetMapping("/user/cart/remove/{id}")
    public ModelAndView removeItemFromCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        cartServ.removeItem(item);
        return shoppingCart();
    }

    @GetMapping("/user/cart/address")
    public ModelAndView createAddress() {
        ModelAndView modelAndView = new ModelAndView("/address");
        modelAndView.addObject("address", new Address());
        return modelAndView;
    }

    @PostMapping("/user/cart/address")
    public String saveAddress(@Valid Address address,
                              BindingResult result,
                              HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (result.hasErrors()) {
            return "address";
        } else {
            address.setUser(user);
            addressServ.saveAddress(address);
            return "redirect:/user/cart/confirm";
        }
    }

    @GetMapping("/user/cart/confirm")
    public ModelAndView cartConfirmation() {
        ModelAndView modelAndView = new ModelAndView("/confirmation");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/user/cart/checkout")
    public String checkout(@Valid Order order, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }
        Map<Item, Integer> itemsInCart = cartServ.getItemsInCart();
        for (Map.Entry<Item, Integer> entry : itemsInCart.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(entry.getKey());
            orderItem.setCount(entry.getValue());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        order.setUser(user);
        BigDecimal total = cartServ.getTotal();
        order.setTotal(total);
        orderServ.saveOrder(order);
        cartServ.checkout(order);
        return "redirect:/user/cart/success";
    }

    @GetMapping("/user/cart/success")
    public ModelAndView cartSuccess() {
        return new ModelAndView("/success");
    }

    @GetMapping("/user/order/{id}")
    public String userOrder(@PathVariable("id") Long id,
                            Model model,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = orderServ.findById(id);
        if (order.getUser().getId().equals(user.getId())) {
            model.addAttribute("order", orderServ.findById(id));
            model.addAttribute("orderItem", orderItemServ.findById(id));
            model.addAttribute("address", addressServ.findById(id));
            model.addAttribute("item", itemServ.findItem(id));
            return "order";
        } else {
            System.out.println("Somebody is trying to access this user's order");
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/admin/user/order/{id}")
    public String userOrderInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServ.findUserById(id));
        model.addAttribute("order", orderServ.findById(id));
        model.addAttribute("orderItem", orderItemServ.findById(id));
        model.addAttribute("address", addressServ.findById(id));
        return "userOrder";
    }
}
