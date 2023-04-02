package com.flameshine.assistant.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.entity.Product;
import com.flameshine.assistant.repository.ProductRepository;
import com.flameshine.assistant.service.Matcher;

// TODO: consider using one SQL query that will search for all matches in the list instead of performing multiple queries one by one
// TODO: investigate recognition mismatches

@Service
@Transactional
@RequiredArgsConstructor
public class ProductMatcher implements Matcher<Product> {

    private final ProductRepository repository;

    @Override
    public Optional<Product> match(List<String> keywords) {
        return keywords.stream()
            .map(repository::findByNameIgnoreCase)
            .findFirst()
            .orElseGet(Optional::empty);
    }
}