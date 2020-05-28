package com.brena.ecommerce.controllers;

import com.brena.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private final ItemServ itemServ;

    @Autowired
    private JavaMailSender javaMailSender;

    public MainController(ItemServ itemServ) {
        this.itemServ = itemServ;
    }

    //  index/home page
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    //  send an email via contact form
    @PostMapping("/contact")
    public ModelAndView contactForm(@RequestParam("userName") String userName,
                                    @RequestParam("userEmail") String userEmail,
                                    @RequestParam("message") String message,
                                    @RequestParam("subject") String subject,
                                    ModelAndView modelAndView) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo();
        simpleMailMessage.setReplyTo(userEmail);
        simpleMailMessage.setFrom(userName);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
        return index();
    }


    //  shows all available items
    @GetMapping("/items")
    public ModelAndView items(ModelAndView modelAndView) {
        modelAndView.addObject("items", itemServ.allItems());
        return modelAndView;
    }

}
