package com.flameshine.advisor.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank(message = "{validation.product.name}")
    private String name;

    @Column(name = "description", unique = true, nullable = false)
    @NotBlank(message = "{validation.product.description}")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "{validation.product.price}")
    private BigDecimal price;

    @Column(name = "currency", nullable = false)
    @NotNull(message = "{validation.product.currency}")
    private String currency;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "{validation.product.quantity}")
    private Integer quantity;
}