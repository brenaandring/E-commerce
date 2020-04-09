package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.ItemServ;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/items/new")
    public String newItem(@ModelAttribute("item") Item item) {
        return "new.jsp";
    }

    @PostMapping(value="/items")
    public String create(@Valid @ModelAttribute("item") Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }
//
//    // show an item
//    @GetMapping("/items/{id}")
//    public String showItem(@PathVariable("id") Long id, Model model) {
//        Item item = itemServ.findItem(id);
//        model.addAttribute("item", item);
//        return "show.jsp";
//    }
//
//    // edit an item
//    @GetMapping("/items/{id}/edit")
//    public String editItem(@PathVariable("id") Long id, Model model) {
//        Item item = itemServ.findItem(id);
//        model.addAttribute("item", item);
//        return "edit.jsp";
//    }
//
//    @PostMapping(value="/items/{id}")
//    public String update(@Valid @ModelAttribute("item") Item item, BindingResult result) {
//        if (result.hasErrors()) {
//            return "edit.jsp";
//        } else {
//            itemServ.saveItem(item);
//            return "redirect:/dashboard";
//        }
//    }
//
//    // delete an item
//    @PostMapping(value="/books/{id}")
//    public String destroy(@PathVariable("id") Long id) {
//        itemServ.deleteItem(id);
//        return "redirect:/dashboard";
//    }

}
