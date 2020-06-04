package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Contact;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class MainController {
    private final ItemServ itemServ;

    @Autowired
    private final JavaMailSender javaMailSender;

    public MainController(ItemServ itemServ, JavaMailSender javaMailSender) {
        this.itemServ = itemServ;
        this.javaMailSender = javaMailSender;
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
    public ModelAndView items(ModelAndView modelAndView) {
        modelAndView.setViewName("items");
        modelAndView.addObject("items", itemServ.allItems());
        return modelAndView;
    }


}
