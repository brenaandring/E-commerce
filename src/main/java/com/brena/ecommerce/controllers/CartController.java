package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Address;
import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final CartServ cartServ;
    private final AddressServ addressServ;
    private final OrderServ orderServ;

    public CartController(UserServ userServ, ItemServ itemServ, CartServ cartServ, AddressServ addressServ, OrderServ orderServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.cartServ = cartServ;
        this.addressServ = addressServ;
        this.orderServ = orderServ;
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
    public String saveAddress(@Valid Address address, BindingResult result, HttpServletRequest request) {
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

    @GetMapping("/user/cart/remove/item/{id}")
    public ModelAndView removeItemFromCartConfirmation(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        cartServ.removeItem(item);
        return cartConfirmation();
    }
//    @GetMapping("/user/cart/checkout")
//    public ModelAndView checkout() {
//
//        cartServ.checkout();
//        return cartSuccess();
//    }
//

    @GetMapping("/user/cart/checkout")
    public String checkout(@Valid Order order, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Address address = addressServ.findByUser(user);
        order.setUser(user);
        order.setAddress(address);
        orderServ.saveOrder(order);
        cartServ.checkout(order);
        return "redirect:/user/cart/success";
    }

    @GetMapping("/user/cart/success")
    public ModelAndView cartSuccess() {
        return new ModelAndView("/success");
    }
}
