package com.flameshine.advisor.service;

import java.util.Map;
import java.math.BigDecimal;

import com.flameshine.advisor.entity.Product;

public interface CartOperator {

    void addById(Long id);

    void removeById(Long id);

    void checkout();

    BigDecimal total();

    Map<Product, Integer> products();
}