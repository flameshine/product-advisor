package com.flameshine.advisor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.repository.ProductRepository;
import com.flameshine.advisor.entity.Product;
import com.flameshine.advisor.service.Matcher;

/**
 * Implementation of the {@link Matcher} that interacts with products.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ProductMatcher implements Matcher<Product> {

    private final ProductRepository repository;

    @Override
    public List<Product> match(List<String> keywords) {
        var formattedKeywords = String.join("|", keywords);
        return repository.findByKeywordsIgnoringCase(formattedKeywords);
    }
}