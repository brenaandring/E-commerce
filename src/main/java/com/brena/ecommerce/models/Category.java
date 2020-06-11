package com.brena.ecommerce.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Size(min = 3, max = 50, message = "CATEGORY is required and it must have between 3 - 50 characters")
    @Column(name = "name")
    private String name;

    public Category() {
    }

    //  model relationships
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Item> items;

    //  getters and setters
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
