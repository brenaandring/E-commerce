package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import com.brena.ecommerce.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping({"/api"})
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

    // user registration
    @GetMapping("/registration")
    public String register(@Valid @ModelAttribute("user") User user) {
        return "register.jsp";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "register.jsp";
        }
        if (userServ.countAdmins() < 1) {
            userServ.saveUserWithAdminRole(user);
        } else {
            userServ.saveWithUserRole(user);
        }
        return "redirect:/dashboard";
    }

    // user login
    @RequestMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "login.jsp";
    }

    // non-admin dashboard
    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userServ.findByUsername(username));
        return "dashboard.jsp";
    }

    // admin dashboard
    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("admin", userServ.findByUsername(username));
        model.addAttribute("users", userServ.allUsers());
        model.addAttribute("item", itemServ.allItems());
        return "admin.jsp";
    }

    // admin: delete user
    @RequestMapping("/users/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        userServ.deleteUser(id);
        return "redirect:/admin";
    }
}
