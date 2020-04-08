package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Role;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.RoleRepo;
import com.brena.ecommerce.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServ {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServ(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // saves a user with only the user role
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName("ROLE_USER"));
        userRepo.save(user);
    }

    // saves a user with only the admin role
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
    }

    // update a user
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    // find user by username
    public User findByUsername(String email) {
        return userRepo.findByEmail(email);
    }

    // admin
    public Long countAdmins() {
        List<User> users = (List<User>) userRepo.findAll();
        Long count = (long) 0;
        for(User user: users) {
            List<Role> roles = user.getRoles();
            for(Role role: roles){
                if(role.getName().equals("ROLE_ADMIN")) {
                    count ++;
                }
            }
        }
        return count;

    }

    // find user by id
    public User findUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    // show all users
    public List<User> allUsers() {
        return userRepo.findAll();
    }

    // deletes a user
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}