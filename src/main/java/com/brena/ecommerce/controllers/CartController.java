package com.brena.ecommerce.controllers;

import com.brena.ecommerce.services.ItemServ;
import com.brena.ecommerce.services.UserServ;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    private final UserServ userServ;
    private final ItemServ itemServ;

    public CartController(UserServ userServ, ItemServ itemServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
    }
}
