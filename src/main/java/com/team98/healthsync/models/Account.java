package com.team98.healthsync.models;

import com.team98.healthsync.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty(message = "Email is required.")
    private String email;

    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{7,}$",message="Enter a strong Password of minimum 7 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Account() {
    }

    public Account(@Email @NotEmpty(message = "Email is required.") String email,
                   @Pattern(regexp = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$",
                           message = "Enter a strong Password of minimum 7 characters") String password, @NotEmpty UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}



