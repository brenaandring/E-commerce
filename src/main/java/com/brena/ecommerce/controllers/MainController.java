package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Contact;
import com.brena.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Properties;

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
        Contact contact = new Contact();
        return new ModelAndView("index");
    }

    @PostMapping("/contact")
    public ModelAndView contactForm(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message,
                                    @RequestParam("subject") String subject, ModelAndView modelAndView, @Valid Contact contact, BindingResult result) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String content = "Name: " + contact.getName() + " ";
            content += "Email: " + contact.getEmail() + " ";
            content += "Message: " + contact.getMessage();
        if (result.hasErrors()) {
            modelAndView.addObject("errorMessage", "Invalid submission, please try again");
        } else {
            simpleMailMessage.setTo("maskwithlove19@gmail.com");
            simpleMailMessage.setText(content);
            simpleMailMessage.setReplyTo(email);
            simpleMailMessage.setSubject(subject);
            javaMailSender.send(simpleMailMessage);
        }
        return index();
    }

    //  shows all available items
    @GetMapping("/items")
    public ModelAndView items(ModelAndView modelAndView) {
        modelAndView.addObject("items", itemServ.allItems());
        return modelAndView;
    }


}
