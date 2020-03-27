package com.brena.ecommerce.services;

import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServ {
    private final UserRepo userRepo;

    public UserServ(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }

    // find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    // find user by id
    public User findUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            return BCrypt.checkpw(password, user.getPassword());
        }
    }
}