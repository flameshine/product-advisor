package com.flameshine.assistant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.repository.ProductRepository;
import com.flameshine.assistant.entity.Product;
import com.flameshine.assistant.service.Matcher;

// TODO: investigate recognition mismatches

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