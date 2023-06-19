package com.flameshine.advisor.service.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import com.google.common.base.Preconditions;

import com.flameshine.advisor.service.CartOperator;
import com.flameshine.advisor.repository.ProductRepository;
import com.flameshine.advisor.entity.Product;

/**
 * Implementation of the {@link CartOperator}.
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartOperatorImpl implements CartOperator {

    private final ProductRepository repository;
    private final Map<Product, Integer> products;

    @Autowired
    public CartOperatorImpl(ProductRepository repository) {
        this.repository = repository;
        this.products = new HashMap<>();
    }

    @Override
    public void addById(Long id) {
        var product = retrieveAndValidateById(id);
        products.merge(product, 1, Integer::sum);
    }

    @Override
    public void removeById(Long id) {

        var product = retrieveAndValidateById(id);
        var quantity = products.get(product);

        if (quantity > 1) {
            products.replace(product, quantity - 1);
        } else {
            products.remove(product);
        }
    }

    @Override
    @Transactional
    public void checkout() {

        for (var productEntry : products.entrySet()) {
            var productFromCart = productEntry.getKey();
            var productFromDb = retrieveAndValidateById(productFromCart.getId());
            productFromCart.setQuantity(productFromDb.getQuantity() - productEntry.getValue());
        }

        repository.saveAll(
            products.keySet()
        );

        products.clear();
    }

    @Override
    public BigDecimal total() {
        return products.entrySet().stream()
            .map(e -> e.getKey().getPrice().multiply(new BigDecimal(e.getValue())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    @Override
    public Map<Product, Integer> products() {
        return Collections.unmodifiableMap(products);
    }

    private Product retrieveAndValidateById(Long id) {
        var itemFromDatabase = repository.findById(id);
        Preconditions.checkState(itemFromDatabase.isPresent(), "Product with id '%s' not found", id);
        return itemFromDatabase.get();
    }
}