package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import com.brena.ecommerce.component.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

import static com.brena.ecommerce.services.OrderServ.*;

@Controller
public class UserController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final UserValidator userValidator;
    private final OrderServ orderServ;

    public UserController(UserServ userServ,
                          ItemServ itemServ,
                          UserValidator userValidator,
                          OrderServ orderServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.userValidator = userValidator;
        this.orderServ = orderServ;
    }

    private static final Map<String, Integer> statusLabels = new LinkedHashMap<String, Integer>() {{
        put(STATUS_NEW, 0);
        put(STATUS_SHIPPED, 1);
        put(STATUS_CANCELLED, 2);
    }};

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
                              @RequestParam(value = "logout", required = false) String logout) {
        modelAndView.setViewName("login");
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
                                  ModelAndView modelAndView,
                                  @RequestParam(value = "status", required = false, defaultValue = "asc")
                                              Optional<String> statusParam) {
        modelAndView.setViewName("admin");
        String email = principal.getName();
        modelAndView.addObject("admin", userServ.findByEmail(email));
        modelAndView.addObject("users", userServ.allUsers());
        modelAndView.addObject("items", itemServ.allItems());
        List<Order> orders = orderServ.allOrders();
        Comparator<Order> status = Comparator.comparing(order -> order == null ? 0 :
                statusLabels.getOrDefault(order.getStatus(), 0));
        if (statusParam.isPresent() && "desc".equals(statusParam.get())) {
            status = status.reversed();
        }
        orders.sort(status);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    //  admin-only: view user info
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
