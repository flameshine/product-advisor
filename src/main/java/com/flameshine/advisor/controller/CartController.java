package com.flameshine.advisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.service.CartOperator;
import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/cart" page, which allows a user to interact with purchased products.
 */

@Controller
@RequestMapping(WebPaths.CART)
@RequiredArgsConstructor
public class CartController {

    private final CartOperator operator;

    @GetMapping
    public ModelAndView cart() {
        return new ModelAndView(WebPaths.CART)
            .addObject("products", operator.products())
            .addObject("total", operator.total());
    }

    @GetMapping("/add/{id}")
    public ModelAndView add(@PathVariable("id") Long id) {
        operator.addById(id);
        return cart();
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        operator.removeById(id);
        return cart();
    }

    @GetMapping("/checkout")
    public ModelAndView checkout() {
        operator.checkout();
        return cart();
    }
}