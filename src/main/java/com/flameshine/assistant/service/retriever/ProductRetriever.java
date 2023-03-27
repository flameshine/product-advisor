package com.flameshine.assistant.service.retriever;

import java.util.List;
import java.util.Optional;

import com.flameshine.assistant.model.Product;

public interface ProductRetriever {
    Optional<Product> retrieve(List<String> keywords);
}