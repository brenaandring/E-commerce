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
    private final ReviewServ reviewServ;

    public MainController(ItemServ itemServ, ReviewServ reviewServ) {
        this.itemServ = itemServ;
        this.reviewServ = reviewServ;
    }

    //  index/home page

//    @RequestMapping("/")
//    public String index() {
//        return "index";
//    }
    @GetMapping("/")
    public ModelAndView index() {
//        List<Item> items = itemServ.allItems();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", itemServ.allItems());
//        modelAndView.addObject("reviews", reviewServ.allReviews());
        modelAndView.setViewName("/index");
        return modelAndView;
    }
//    @RequestMapping("/")
//    public String index(Model model) {
//        model.addAttribute("item", itemServ.allItems());
//        model.addAttribute("review", reviewServ.allReviews());
//        return "index";
//    }

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
