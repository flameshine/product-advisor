package com.flameshine.advisor.service;

import java.util.Map;
import java.math.BigDecimal;

import com.flameshine.advisor.entity.Product;

/**
 * Interface that provides all needed methods for interacting with the product cart.
 */

public interface CartOperator {

    void addById(Long id);

    void removeById(Long id);

    void checkout();

    BigDecimal total();

    Map<Product, Integer> products();
}