package com.brena.ecommerce.models;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "role_id")
    private Long id;

    @Column (name = "name")
    private String name;

    // model relationships
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
    }

    // getters and setters
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
