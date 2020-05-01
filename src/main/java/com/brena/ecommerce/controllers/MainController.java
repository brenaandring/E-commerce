package com.brena.ecommerce.controllers;

import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final ItemServ itemServ;
    private final ReviewServ reviewServ;

    public MainController(ItemServ itemServ, ReviewServ reviewServ) {
        this.itemServ = itemServ;
        this.reviewServ = reviewServ;
    }

    //  index/home page
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("item", itemServ.allItems());
        model.addAttribute("review", reviewServ.allReviews());
        return "index.jsp";
    }

    //  about page
    @GetMapping("/about")
    public String about() {
        return "about.jsp";
    }

    //  contact page
    @GetMapping("/contact")
    public String contact() {
        return "contact.jsp";
    }
}
