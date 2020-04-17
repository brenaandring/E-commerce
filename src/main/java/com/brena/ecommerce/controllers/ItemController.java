package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.ItemServ;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class ItemController {
    private final ItemServ itemServ;

    public ItemController(ItemServ itemServ) {
        this.itemServ = itemServ;
    }

    // index
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("item", itemServ.allItems());
        return "index.jsp";
    }

    // admin: create a new item
    @RequestMapping("/items/new")
    public String newItem(Model model) {
        model.addAttribute("item", new Item());
        return "new.jsp";
    }

    @PostMapping("/items")
    public String create(@Valid @ModelAttribute("item") Item item, /*@RequestParam MultipartFile file,*/ BindingResult result) {
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
            byte[] image = new byte[0];
            try {
                image = item.getUploadFile().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            item.setImage(image);
            itemServ.saveItem(item);
            return "redirect:/admin";
        }
    }

    // show an item
    @GetMapping("/items/{id}")
    public String showItem(@PathVariable("id") Long id, Model model) {
        Item item = itemServ.findItem(id);
        model.addAttribute("item", item);
        if (item.getImage() != null && item.getImage().length > 0) {
            String imageAsString = new String(Base64.encodeBase64(item.getImage()));
            model.addAttribute("itemImage", imageAsString);
        }
        return "show.jsp";
    }

    // admin: edit an item
    @GetMapping("/items/edit/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        model.addAttribute("item", itemServ.findItem(id));
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

    // don't forget to change this before deployment
    @PostMapping("/cancel")
    public String cancel() {
        return "redirect:/admin";
    }
}
