package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    private final UserServ userServ;
    private final ItemServ itemServ;
    private final AddressServ addressServ;
    private final OrderServ orderServ;
    private final OrderItemServ orderItemServ;

    public OrderController(UserServ userServ,
                          ItemServ itemServ,
                          AddressServ addressServ,
                          OrderServ orderServ,
                          OrderItemServ orderItemServ) {
        this.userServ = userServ;
        this.itemServ = itemServ;
        this.addressServ = addressServ;
        this.orderServ = orderServ;
        this.orderItemServ = orderItemServ;
    }

    //  user: view their order information
    @GetMapping("/user/order/{id}")
    public String userOrder(@PathVariable("id") Long id,
                            Model model,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = orderServ.findById(id);
        if (order != null && order.getUser().getId().equals(user.getId())) {
            model.addAttribute("order", orderServ.findById(id));
            model.addAttribute("orderItem", orderItemServ.findById(id));
            model.addAttribute("address", addressServ.findById(id));
            model.addAttribute("item", itemServ.findItem(id));
            return "order";
        } else {
            System.out.println("Somebody is trying to access this user's order");
        }
        return "redirect:/user/dashboard";
    }

    //  admin-only: view user's order information
    @GetMapping("/admin/user/order/{id}")
    public ModelAndView userOrderInfo(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("userOrder");
        modelAndView.addObject("user", userServ.findUserById(id));
        modelAndView.addObject("order", orderServ.findById(id));
        modelAndView.addObject("orderItem", orderItemServ.findById(id));
        modelAndView.addObject("address", addressServ.findById(id));
        return modelAndView;
    }

    @RequestMapping("/admin/user/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderServ.deleteOrder(id);
        return "redirect:/admin";
    }
}
