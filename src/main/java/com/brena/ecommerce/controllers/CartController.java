package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.models.Address;
import com.brena.ecommerce.models.Order;
import com.brena.ecommerce.services.*;

import com.squareup.square.Environment;
import com.squareup.square.api.PaymentsApi;
import com.squareup.square.models.*;
import com.squareup.square.SquareClient;
import com.squareup.square.exceptions.ApiException;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
public class CartController {
    private final ItemServ itemServ;
    private final CartServ cartServ;
    private final AddressServ addressServ;
    private final OrderServ orderServ;

    //  sandbox fake credentials.
    private static final String SQUARE_ACCESS_TOKEN_ENV_VAR = "EAAAEAKAEE1Pk_9XHhI666sDkgbv_ZS7AXqPf-KyCJO6YNDZY9Ta2zTwsvkamoWZ";
    private static final String SQUARE_APP_ID_ENV_VAR = "sandbox-sq0idb-zgCfDsE9CFHRnH6DDmUGVA";
    private static final String SQUARE_LOCATION_ID_ENV_VAR = "S2XEKDH2Z15TA";
    private static final String SQUARE_ENV_ENV_VAR = "sandbox";

    private final SquareClient squareClient;
    private final String squareLocationId;
    private final String squareAppId;
    private final String squareEnvironment;

    public CartController(ItemServ itemServ,
                          CartServ cartServ,
                          AddressServ addressServ,
                          OrderServ orderServ) throws ApiException {
        this.itemServ = itemServ;
        this.cartServ = cartServ;
        this.addressServ = addressServ;
        this.orderServ = orderServ;
        squareEnvironment = mustLoadEnvironmentVariable(SQUARE_ENV_ENV_VAR);
        squareAppId = mustLoadEnvironmentVariable(SQUARE_APP_ID_ENV_VAR);
        squareLocationId = mustLoadEnvironmentVariable(SQUARE_LOCATION_ID_ENV_VAR);

        squareClient = new SquareClient.Builder()
                .environment(Environment.fromString(squareEnvironment))
                .accessToken(mustLoadEnvironmentVariable(SQUARE_ACCESS_TOKEN_ENV_VAR)).build();
    }

    private String mustLoadEnvironmentVariable(String name) {
        if (true) {
            return name;
        }
        String value = System.getenv(name);
        if (value == null || value.length() == 0) {
            throw new IllegalStateException(String.format("The %s environment variable must be set", name));
        }
        return value;
    }

    //  user: view their cart
    @GetMapping("/cart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("items", cartServ.getItemsInCart());
        modelAndView.addObject("total", cartServ.getTotal().toString());
        return modelAndView;
    }

    //  user: add items to their cart
    @GetMapping("/cart/add/{id}")
    public ModelAndView addItemToCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        if (item.getQuantity() > 0) {
            cartServ.addItem(item);
        }
        return shoppingCart();
    }

    //  user: remove items from their cart
    @GetMapping("/cart/remove/{id}")
    public ModelAndView removeItemFromCart(@PathVariable("id") Long id) {
        Item item = itemServ.findItem(id);
        cartServ.removeItem(item);
        return shoppingCart();
    }

    //  user: shows them their cart confirmation
    @GetMapping("/cart/confirm")
    public Object confirm(RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        RedirectView redirectView = new RedirectView("/cart");
        if (cartServ.getItemsInCart().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your cart is empty! Please add an item before proceeding.");
            return redirectView;
        } else {
            modelAndView.setViewName("confirmation");
            modelAndView.addObject("items", cartServ.getItemsInCart());
            modelAndView.addObject("address", new Address());
            modelAndView.addObject("total", cartServ.getTotal().toString());
            modelAndView.addObject("locationId", squareLocationId);
            modelAndView.addObject("appId", squareAppId);
            return modelAndView;
        }
    }

    //  user: checkout and saves information
    @RequestMapping("/cart/checkout")
    public Object checkout(@Valid Order order, RedirectAttributes redirectAttributes,
                           @Valid Address address,
                           BindingResult result, HttpServletRequest request,
                           @ModelAttribute NonceForm form, Map<String, Object> model) throws ApiException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        RedirectView redirectView = new RedirectView("/cart/confirm");
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }
        orderServ.confirmItems(order);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid address/email. Please try again!");
            return redirectView;
        } else {
            addressServ.saveAddress(address, user);
            BigDecimal total = cartServ.getTotal();
            Money bodyAmountMoney = new Money.Builder()
                    .amount(total.longValue() * 100)
                    .currency("USD")
                    .build();
            CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(
                    form.getNonce(),
                    UUID.randomUUID().toString(),
                    bodyAmountMoney)
                    .autocomplete(true)
                    .note("Mask With Love")
                    .build();
            PaymentsApi paymentsApi = squareClient.getPaymentsApi();
            try{
                CreatePaymentResponse response = paymentsApi.createPayment(createPaymentRequest);
                model.put("payment", response.getPayment());
                orderServ.saveNewOrder(order, address, user, total);
                cartServ.checkout(order);
                return cartSuccess();
            } catch (ApiException except) {
                model.put("error", except.getErrors().get(0));
                return "error";
            }
        }
    }

    //  user: shows them the success page
    @GetMapping("/cart/success")
    public ModelAndView cartSuccess() {
        return new ModelAndView("success");
    }
}

