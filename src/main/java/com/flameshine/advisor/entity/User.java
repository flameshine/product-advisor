package com.flameshine.advisor.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity that represents users data.
 */

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
    @NotBlank(message = "{validation.user.username.presence}")
    @Length(min = 5, max = 15, message = "{validation.user.username.length}")
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    @NotBlank(message = "{validation.user.password.presence}")
    @Length(min = 5, message = "{validation.user.password.length}")
    private String password;

    @Transient
    @JsonIgnore
    @NotBlank(message = "{validation.user.password-confirmation}")
    private String passwordConfirmation;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "{validation.user.firstname}")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "{validation.user.lastname}")
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "{validation.user.email}")
    private String email;
}