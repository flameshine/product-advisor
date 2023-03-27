package com.flameshine.assistant.service.retriever.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.model.Product;
import com.flameshine.assistant.repository.ProductRepository;
import com.flameshine.assistant.service.retriever.ProductRetriever;

// TODO: consider using one SQL query that will search for all matches in the list instead of performing multiple queries one by one

@Service
@AllArgsConstructor
public class ProductRetrieverImpl implements ProductRetriever {

    private final ProductRepository repository;

    @Override
    public Optional<Product> retrieve(List<String> keywords) {
        return keywords.stream()
            .map(repository::findByName)
            .findFirst();
    }
}