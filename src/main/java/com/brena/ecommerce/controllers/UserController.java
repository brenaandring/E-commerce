package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.UserServ;
import com.brena.ecommerce.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserServ userServ;
    private UserValidator userValidator;

    public UserController(UserServ userServ, UserValidator userValidator) {
        this.userServ = userServ;
        this.userValidator = userValidator;
    }

    @RequestMapping("/registration")
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

    @RequestMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        String username = principal.getName();
        User currentUser = userServ.findByUsername(username);
        userServ.updateUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        return "dashboard.jsp";
    }

    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        List<User> users = userServ.allUsers();
        model.addAttribute("currentUser", userServ.findByUsername(username));
        model.addAttribute("users", users);
        return "admin.jsp";
    }
}