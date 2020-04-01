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

@Controller
public class RegisterLoginController {
    private final UserServ userServ;
    private final UserValidator userValidator;

    public RegisterLoginController(UserServ userServ, UserValidator userValidator) {
        this.userServ = userServ;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String index() {
        return "index.jsp";
    }

    @GetMapping("/registration")
    public String register(@ModelAttribute("user") User user) {
        return "register.jsp";
    }

    @GetMapping("/login")
    public String login() {
        return "login.jsp";
    }

    @PostMapping(value="/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        userValidator.validate(user, result);
        if(result.hasErrors()) {
            return "register.jsp";
        }
        User i = userServ.registerUser(user);
        session.setAttribute("userId", i.getId());
        return "redirect:/dashboard";
    }

    @PostMapping(value="/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        if(userServ.authenticateUser(email, password)) {
            User i = userServ.findByEmail(email);
            session.setAttribute("userId", i.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "login.jsp";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User i = userServ.findUserById(userId);
        model.addAttribute("user", i);
        return "dashboard.jsp";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
