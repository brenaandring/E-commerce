package com.brena.ecommerce.controllers;

import com.brena.ecommerce.services.ItemServ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
    private final ItemServ itemServ;

    public ItemController(ItemServ itemServ) {
        this.itemServ = itemServ;
    }

    @GetMapping("/")
    public String index() {
        return "index.jsp";
    }
}
