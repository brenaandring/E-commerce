package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Role;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.RoleRepo;
import com.brena.ecommerce.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServ {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServ(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //  save a user with user privilege
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName("ROLE_USER"));
        userRepo.save(user);
    }

    //  save a user with admin privilege
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
    }

    //  find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    //  find user by id
    public User findUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    //  admin
    public Long countAdmins() {
        List<User> users = (List<User>) userRepo.findAll();
        Long count = (long) 0;
        for (User user : users) {
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    count++;
                }
            }
        }
        return count;
    }

    //  show all users
    public List<User> allUsers() {
        return userRepo.findAll();
    }

    //  admin-only: delete a user
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
