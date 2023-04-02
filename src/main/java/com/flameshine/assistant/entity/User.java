package com.flameshine.assistant.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    @Length(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required")
    @Length(min = 5, message = "Password must be greater than 5 characters")
    private String password;

    @Transient
    @JsonIgnore
    @NotBlank(message = "Password confirmation is required")
    private String passwordConfirmation;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "First name is required")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Invalid email")
    private String email;
}