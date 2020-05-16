package com.brena.ecommerce.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 100, message = "A NAME is required and it cannot contain more than 100 characters")
    private String name;

    @Column(name = "street")
    @Size(min = 5, max = 50, message = "A STREET ADDRESS is required and it must contain 5 - 50 characters")
    private String street;

    @Column(name = "city")
    @Size(min = 1, max = 15, message = "A CITY is required and it cannot contain more than 15 characters")
    private String city;

    @Column(name = "state")
    @Size(min = 1, max = 15, message = "A STATE is required and it cannot contain more than 15 characters")
    private String state;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    //  model relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Address() {
    }

    //   getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date());
    }
}
