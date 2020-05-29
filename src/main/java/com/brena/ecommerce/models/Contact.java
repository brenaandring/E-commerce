package com.brena.ecommerce.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Contact {
    @NotBlank
    @Pattern(regexp = "[\\p{L} '-]+", message = "Please provide a valid name")
    private String name;

    @Email(message = "Please provide a valid email")
    @Size(max = 50, message = "Email address is required and it cannot have more than 50 characters")
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
