package com.brena.ecommerce.controllers;

import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//@RestController
@Controller
public class MainController {
    private final ItemServ itemServ;

    public MainController(ItemServ itemServ) {
        this.itemServ = itemServ;
    }

    //  index/home page
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    //  mask page
    @GetMapping("/items")
    public ModelAndView items(ModelAndView modelAndView) {
        modelAndView.addObject("items", itemServ.allItems());
        return modelAndView;
    }

}
