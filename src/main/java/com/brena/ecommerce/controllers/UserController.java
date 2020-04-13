package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.ItemServ;
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
    private ItemServ itemServ;
    private UserValidator userValidator;

    public UserController(UserServ userServ, ItemServ itemServ, UserValidator userValidator) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.userValidator = userValidator;
    }

    // register user
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

    // login user
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
    @RequestMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        String username = principal.getName();
        User currentUser = userServ.findByUsername(username);
        userServ.updateUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        return "dashboard.jsp";
    }

    // admin dashboard
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userServ.findByUsername(username));
        List<User> users = userServ.allUsers();
        model.addAttribute("users", users);
        List<Item> item = itemServ.allItems();
        model.addAttribute("item", item);
        return "admin.jsp";
    }

    // admin: edit user
//    @GetMapping("/users/edit/{id}")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        User user = userServ.findUserById(id);
//        model.addAttribute("user", user);
//        return "editUser.jsp";
//    }
//
//    @PostMapping("/users/{id}")
//    public String update(@Valid @ModelAttribute("user") User user, BindingResult result){
//        if (result.hasErrors()) {
//            return "editUser.jsp";
//        } else {
//            userServ.updateUser(user);
//            return "redirect:/admin";
//        }
//    }

    // admin: delete user
    @RequestMapping("/users/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        userServ.deleteUser(id);
        return "redirect:/admin";
    }
}