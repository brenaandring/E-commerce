package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.ItemServ;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ItemController {
    private ItemServ itemServ;

    public ItemController(ItemServ itemServ) {
        this.itemServ = itemServ;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Item> item = itemServ.allItems();
        model.addAttribute("item", item);
        return "index.jsp";
    }

    // admin: create a new item
    @PostMapping("/items/new")
    public String newItem(Model model) {
        model.addAttribute("item", new Item());
        return "new.jsp";
    }

    @PostMapping("/items")
    public String create(@Valid @ModelAttribute("item") Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    // show an item
    @GetMapping("/items/{id}")
    public String showItem(@PathVariable("id") Long id, Model model) {
        Item item = itemServ.findItem(id);
        model.addAttribute("item", item);
        return "show.jsp";
    }

    // admin: edit an item
    @GetMapping("/items/edit/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        Item item = itemServ.findItem(id);
        model.addAttribute("item", item);
        return "edit.jsp";
    }

    @PostMapping("/items/{id}")
    public String update(@Valid @ModelAttribute("item") Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "edit.jsp";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    // admin: delete an item
    @RequestMapping("/items/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        itemServ.deleteItem(id);
        return "redirect:/admin";
    }

    // admin: cancel button and redirect
//    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
//    public String rateHandler(HttpServletRequest request) {
//        String prev = request.getHeader("Referer");
//        return "redirect:" + prev;
//    }

    @PostMapping("/cancel")
    public String cancel() {
        return "redirect:/admin";
    }

}
