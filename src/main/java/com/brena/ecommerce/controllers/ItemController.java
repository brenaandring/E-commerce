package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class ItemController {
    private final ItemServ itemServ;
    private final ReviewServ reviewServ;
    private final UserServ userServ;

    public ItemController(ItemServ itemServ, ReviewServ reviewServ, UserServ userServ) {
        this.itemServ = itemServ;
        this.reviewServ = reviewServ;
        this.userServ = userServ;
    }

    private static final Map<Integer, Integer> ratings = new LinkedHashMap<Integer, Integer>() {{
        put(5, 5);
        put(4, 4);
        put(3, 3);
        put(2, 2);
        put(1, 1);
    }};

    //  admin-only: create a new item
    @GetMapping("/items/create")
    public ModelAndView createItem() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("item", new Item());
        return modelAndView;
    }
    @PostMapping("/items/create")
    public String create(@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

//    @PostMapping("/items/create")
//    public ModelAndView create(@Valid Item item, BindingResult result) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (result.hasErrors()) {
//            modelAndView.setViewName("/items/create");
//        } else {
//            itemServ.saveItem(item);
//        }
//        modelAndView.setViewName("/admin");
//        return modelAndView;
//    }

    //  show an item
    @GetMapping("/items/{id}")
    public ModelAndView showItem(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/read");
        modelAndView.addObject("item", itemServ.findItem(id));
        modelAndView.addObject("review", new Review());
//        modelAndView.addObject("ratings", ratings);
        return modelAndView;
    }

    //  admin-only: edit an item
    @GetMapping("/items/edit/{id}")
    public ModelAndView editItem(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/update");
        modelAndView.addObject("item", itemServ.findItem(id));
        return modelAndView;
    }

//    @GetMapping("/items/edit/{id}")
//    public String editItem(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("item", itemServ.findItem(id));
//        return "edit.jsp";
//    }

    @PostMapping("/items/edit/{id}")
    public String update(@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "update.html";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    //  admin-only: delete an item
    @RequestMapping("/items/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        itemServ.deleteItem(id);
        return "redirect:/admin";
    }

    //  create an item review
    @PostMapping("/items/review/{id}")
    public String createReview(@PathVariable("id") Long id, @Valid Review review, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            return "read.html";
        } else {
            Item item = itemServ.findItem(id);
            review.setItem(item);
            reviewServ.saveReview(review);
            return "redirect:/items/{id}";
        }
    }

    //  admin-only: delete a review
    @RequestMapping("/review/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewServ.deleteReview(id);
        return "redirect:/admin";
    }
}
