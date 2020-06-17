package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.ItemRepo;
import com.brena.ecommerce.repositories.OrderRepo;
import com.brena.ecommerce.repositories.UserRepo;
import com.brena.ecommerce.services.*;
import com.brena.ecommerce.validator.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final UserRepo userRepo;
    private final ItemServ itemServ;
    private final UserValidator userValidator;
    private final OrderServ orderServ;
    private final OrderRepo orderRepo;
    private final ItemRepo itemRepo;

    public UserController(UserServ userServ,
                          ItemServ itemServ,
                          UserValidator userValidator,
                          OrderServ orderServ, OrderRepo orderRepo,
                          ItemRepo itemRepo, UserRepo userRepo) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.userValidator = userValidator;
        this.orderServ = orderServ;
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
    }

    //  user registration
    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createUser(@Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        ModelAndView modelAndView = new ModelAndView();
        User userFound = userServ.findByEmail(user.getEmail());
        if (userFound != null) {
            modelAndView.addObject("errorMessage", "There is already a user with the " +
                    "email provided, please use a different email address");
            modelAndView.setViewName("registration");
        } else {
            if (result.hasErrors()) {
                modelAndView.setViewName("registration");
            } else {
                if (userServ.countAdmins() < 1) {
                    userServ.saveUserWithAdminRole(user);
                } else {
                    userServ.saveWithUserRole(user);
                }
                modelAndView.setViewName("/login");
            }
        }
        return modelAndView;
    }

    //  admin/user login
    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView,
                              @RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              HttpServletRequest request) {
        modelAndView.setViewName("login");
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid credentials. Please try again.");
        }
        if (logout != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            modelAndView.addObject("logoutMessage", "Logout successful!");
        }
        return modelAndView;
    }

    //  user dashboard
    @GetMapping("/user/dashboard")
    public ModelAndView dashboard(Principal principal,
                                  HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("dashboard");
        String email = principal.getName();
        User user = userServ.findByEmail(email);
        modelAndView.addObject("user", user);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return modelAndView;
    }

    //  admin dashboard
    @GetMapping("/admin")
    public ModelAndView adminPage(Principal principal,
                                  ModelAndView modelAndView, HttpServletRequest request,
                                  @PageableDefault(value = 5, sort = "status", direction = Sort.Direction.DESC) Pageable pageable) {
        modelAndView.setViewName("admin");
        String email = principal.getName();
        User admin = userServ.findByEmail(email);
        HttpSession session = request.getSession();
        session.setAttribute("admin", admin);
        modelAndView.addObject("admin", admin);
        Page<Order> orders = orderServ.allOrders(pageable);
        modelAndView.addObject("page", orders);
        return modelAndView;
    }

    @GetMapping("/admin/items")
    public ModelAndView adminItems(ModelAndView modelAndView,
                                   @PageableDefault(value = 5) Pageable pageable) {
        modelAndView.setViewName("adminItems");
        Page<Item> page = itemRepo.findAll(pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/admin/users")
    public ModelAndView adminUsers(ModelAndView modelAndView,
                                   @PageableDefault(value = 5) Pageable pageable) {
        modelAndView.setViewName("adminUsers");
        Page<User> page = userRepo.findAll(pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/admin/user/info/{id}")
    public ModelAndView userInfo(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("user", userServ.findUserById(id));
        return modelAndView;
    }

    //  admin-only: delete a user
    @RequestMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServ.deleteUser(id);
        return "redirect:/admin";
    }
}
