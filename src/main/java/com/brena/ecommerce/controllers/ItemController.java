package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

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

    //  admin-only: create a new item
    @GetMapping("/admin/items/create")
    public ModelAndView createItem() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("item", new Item());
        return modelAndView;
    }

    @PostMapping("/admin/items/create")
    public String create(@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    //  show an item
    @GetMapping("/items/{id}")
    public ModelAndView showItem(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/read");
        modelAndView.addObject("item", itemServ.findItem(id));
        modelAndView.addObject("review", new Review());
        return modelAndView;
    }

    //  admin-only: edit an item
    @GetMapping("/admin/items/edit/{id}")
    public ModelAndView editItem(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/update");
        modelAndView.addObject("item", itemServ.findItem(id));
        return modelAndView;
    }

    @PostMapping("/admin/items/edit/{id}")
    public String update(@Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "update.html";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    //  admin-only: delete an item
    @RequestMapping("/admin/items/delete/{id}")
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
    @RequestMapping("/admin/review/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewServ.deleteReview(id);
        return "redirect:/admin";
    }
}
