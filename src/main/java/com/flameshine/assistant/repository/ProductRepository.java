package com.flameshine.assistant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flameshine.assistant.model.Product;

// TODO: add method to search by keyword and tie this functionality to the rest of components

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> { }