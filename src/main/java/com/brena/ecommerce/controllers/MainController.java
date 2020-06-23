package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Contact;
import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.repositories.ItemRepo;
import com.brena.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class MainController {
    private final ItemServ itemServ;
    private final ItemRepo itemRepo;
    private final CategoryServ categoryServ;

    @Autowired
    private final JavaMailSender javaMailSender;

    public MainController(ItemServ itemServ,
                          JavaMailSender javaMailSender,
                          ItemRepo itemRepo,
                          CategoryServ categoryServ) {
        this.itemServ = itemServ;
        this.javaMailSender = javaMailSender;
        this.itemRepo = itemRepo;
        this.categoryServ = categoryServ;
    }

    //  index/home page
    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/contact")
    public RedirectView contactForm(@RequestParam("email") String email,
                                    @RequestParam("subject") String subject,
                                    @Valid Contact contact, BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        RedirectView redirectView = new RedirectView("/");
        String content = "Name: " + contact.getName() + " ";
            content += "Email: " + contact.getEmail() + " ";
            content += "Message: " + contact.getMessage();
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Invalid submission, please try again");
        } else {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> debug");
            String gmail_email = System.getenv("GMAIL_EMAIL");
            System.out.println("gmail_email = " + gmail_email);
            String gmail_password = System.getenv("GMAIL_PASSWORD");
            System.out.println("gmail_password = " + gmail_password);

            simpleMailMessage.setTo("maskwithlove19@gmail.com");
            simpleMailMessage.setText(content);
            simpleMailMessage.setReplyTo(email);
            simpleMailMessage.setSubject(subject);
            javaMailSender.send(simpleMailMessage);
            redirectAttributes.addFlashAttribute("message", "Message sent. Thank you!");
        }
        return redirectView;
    }

    //  shows all available items
    @GetMapping("/items")
    public ModelAndView items(@PageableDefault(value = 9) Pageable pageable,
                              ModelAndView modelAndView) {
        modelAndView.setViewName("items");
        modelAndView.addObject("category", categoryServ.allCategories());
        Page<Item> page = itemRepo.findAll(pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/items/category/{id}")
    public ModelAndView getItemsCategory(@PathVariable("id") Long id,
                                         @PageableDefault(value = 9) Pageable pageable,
                                         ModelAndView modelAndView) {
        modelAndView.setViewName("items");
        modelAndView.addObject("category", categoryServ.allCategories());
        Page<Item> page = itemRepo.findAllItemsByCategoryId(id, pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}


