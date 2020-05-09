package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import com.brena.ecommerce.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final UserValidator userValidator;

    public UserController(UserServ userServ, ItemServ itemServ, UserValidator userValidator) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.userValidator = userValidator;
    }

    //  user registration
    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createUser(@Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("/registration");
        }
        if (userServ.countAdmins() < 1) {
            userServ.saveUserWithAdminRole(user);
        } else {
            userServ.saveWithUserRole(user);
        }
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    //  admin/user login
    @RequestMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("user") User user, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid credentials. Please try again.");
        }
        if (logout != null) {
            modelAndView.addObject("logoutMessage", "Logout successful!");
        }
        modelAndView.setViewName(("/login"));
        return modelAndView;
    }

//    @RequestMapping("/login")
//    public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
//        if (error != null) {
//            model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
//        }
//        if (logout != null) {
//            model.addAttribute("logoutMessage", "Logout Successful!");
//        }
//        return "login.jsp";
//    }

    //  user dashboard
    @GetMapping("/dashboard")
    public ModelAndView dashboard(Principal principal, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        String email = principal.getName();
        modelAndView.addObject("user", userServ.findByEmail(email));
        return modelAndView;
    }
//    @GetMapping("/dashboard")
//    public String dashboard(Principal principal, Model model) {
//        String email = principal.getName();
//        model.addAttribute("user", userServ.findByEmail(email));
//        return "dashboard.jsp";
//    }

    //  admin dashboard
    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("admin", userServ.findByEmail(email));
        model.addAttribute("users", userServ.allUsers());
        model.addAttribute("item", itemServ.allItems());
        return "admin.jsp";
    }

    //  admin-only: delete a user
    @RequestMapping("/users/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        userServ.deleteUser(id);
        return "redirect:/admin";
    }
}
