package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import com.brena.ecommerce.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final UserValidator userValidator;
    private final ReviewServ reviewServ;
    private final AddressServ addressServ;

    public UserController(UserServ userServ, ItemServ itemServ, UserValidator userValidator, ReviewServ reviewServ, AddressServ addressServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.userValidator = userValidator;
        this.reviewServ = reviewServ;
        this.addressServ = addressServ;
    }

    //  user registration
    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView("/registration");
        User user = new User();
        modelAndView.addObject("user", user);
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
    public ModelAndView login(@Valid User user, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid credentials. Please try again.");
        }
        if (logout != null) {
            modelAndView.addObject("logoutMessage", "Logout successful!");
        }
        return modelAndView;
    }

    //  user dashboard
    @GetMapping("/user/dashboard")
    public ModelAndView dashboard(Principal principal, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/dashboard");
        String email = principal.getName();
        User user = userServ.findByEmail(email);
        modelAndView.addObject("user", user);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return modelAndView;
    }

    //  admin dashboard
    @GetMapping("/admin")
    public ModelAndView adminPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        String email = principal.getName();
        modelAndView.addObject("admin", userServ.findByEmail(email));
        modelAndView.addObject("users", userServ.allUsers());
        modelAndView.addObject("item", itemServ.allItems());
        return modelAndView;
    }

    //  admin-only: delete a user
    @RequestMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServ.deleteUser(id);
        return "redirect:/admin";
    }
}
