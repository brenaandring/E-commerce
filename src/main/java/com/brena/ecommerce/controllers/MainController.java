package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("items", itemServ.allItems());
        return modelAndView;
    }

    //  about page
    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("/about");
    }

    //  contact page
    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("/contact");
    }
}
