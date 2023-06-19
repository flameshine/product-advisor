package com.flameshine.advisor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.flameshine.advisor.entity.Product;

/**
 * Spring Data JPA repository for interacting with the {@link Product} entity.
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product WHERE LOWER(name) REGEXP (:keywordsFormatted)", nativeQuery = true)
    List<Product> findByKeywordsIgnoringCase(@Param("keywordsFormatted") String keywordsFormatted);
}