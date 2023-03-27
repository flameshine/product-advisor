package com.flameshine.assistant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.flameshine.assistant.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByName(String name);
}