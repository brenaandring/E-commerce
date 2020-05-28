package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    //  user: view their cart
    @GetMapping("/user/cart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    //  user: add items to their cart
    @GetMapping("/user/cart/add/{id}")
    public ModelAndView addItemToCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        if (item.getQuantity() > 0) {
            cartServ.addItem(item);
        }
        return shoppingCart();
    }

    //  user: remove items from their cart
    @GetMapping("/user/cart/remove/{id}")
    public ModelAndView removeItemFromCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        cartServ.removeItem(item);
        return shoppingCart();
    }

    //  user: shows them their cart confirmation
    @GetMapping("/user/cart/confirm")
    public RedirectView confirm(RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/user/cart");
        RedirectView redirectView2 = new RedirectView("/user/cart/confirmation");
        if (cartServ.getItemsInCart().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your cart is empty! Please add an item before proceeding.");
            return redirectView;
        } else {
            return redirectView2;
        }
    }

    @GetMapping("/user/cart/confirmation")
    public ModelAndView cartConfirmation(ModelAndView modelAndView) {
        modelAndView.setViewName("confirmation");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("address", new Address());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    //  user: checkout and saves information
    @RequestMapping("/user/cart/checkout")
    public RedirectView checkout(@Valid Order order, RedirectAttributes redirectAttributes, @Valid Address address, BindingResult result, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        RedirectView redirectView = new RedirectView("/user/cart/confirmation");
        RedirectView redirectView2 = new RedirectView("/user/cart/success");
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
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid address. Please try again!");
            return redirectView;
        } else {
            address.setUser(user);
            order.setAddress(address);
            addressServ.saveAddress(address);
            order.setUser(user);
            BigDecimal total = cartServ.getTotal();
            order.setTotal(total);
            orderServ.saveOrder(order);
            cartServ.checkout(order);
            return redirectView2;
        }
    }

    //  user: shows them the success page
    @GetMapping("/user/cart/success")
    public ModelAndView cartSuccess() {
        return new ModelAndView("success");
    }

    //  user: view their order information
    @GetMapping("/user/order/{id}")
    public String userOrder(@PathVariable("id") Long id,
                            Model model,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = orderServ.findById(id);
        if (order != null && order.getUser().getId().equals(user.getId())) {
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

    //  admin-only: view user's order information
    @GetMapping("/admin/user/order/{id}")
    public ModelAndView userOrderInfo(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("userOrder");
        modelAndView.addObject("user", userServ.findUserById(id));
        modelAndView.addObject("order", orderServ.findById(id));
        modelAndView.addObject("orderItem", orderItemServ.findById(id));
        modelAndView.addObject("address", addressServ.findById(id));
        return modelAndView;
    }
}

