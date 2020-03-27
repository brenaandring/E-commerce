package com.brena.ecommerce.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 100, message="A FIRST NAME is required and it must contain 2 - 100 characters")
    private String firstName;
    @Size(min = 2, max = 100, message="A LAST NAME is required and it must contain 2 - 100 characters")
    private String lastName;
    @Size(min = 4, max = 100, message="An ADDRESS is required and it must contain 4 - 100 characters")
    private String address;
    @Size(min = 2, max = 50, message="A CITY is required")
    private String city;
    @Size(min = 2, max = 2, message="A STATE is required")
    private String state;
    @Size(min = 5, max = 5, message="A ZIP CODE is required")
    private String zip;
    @Email(message="Email must be valid")
    private String email;
    @Size(min=8, message="Password must have a minimum of 8 characters")
    private String password;
    @Transient
    @Size(min = 8, message = "Password did not match!")
    private String passwordConfirmation;
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
