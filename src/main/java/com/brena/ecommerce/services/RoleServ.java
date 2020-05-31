package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Role;
import com.brena.ecommerce.repositories.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServ {
    private final RoleRepo roleRepo;

    public RoleServ(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public List<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }
}
