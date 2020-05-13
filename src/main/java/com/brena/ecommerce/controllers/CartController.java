package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.services.CartServ;
import com.brena.ecommerce.services.ItemServ;
import com.brena.ecommerce.services.UserServ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final CartServ cartServ;

    public CartController(UserServ userServ, ItemServ itemServ, CartServ cartServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.cartServ = cartServ;
    }

    @GetMapping("/cart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/cart");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/cart/add/{id}")
    public ModelAndView addItemToCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        if (item.getQuantity() > 0) {
            cartServ.addItem(item);
        }
        return shoppingCart();
    }

    @GetMapping("/cart/remove/{id}")
    public ModelAndView removeItemFromCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        cartServ.removeItem(item);
        return shoppingCart();
    }

    @GetMapping("/cart/checkout")
    public ModelAndView checkout() {
        cartServ.checkout();
        return shoppingCart();
    }
}
