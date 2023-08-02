package com.exam.service;

import com.exam.module.User;
import com.exam.repository.IRoleRepo;
import com.exam.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //creating user
    public User createUser (User user) {
        Optional<User> isPresent = userRepo.findByUserName(user.getUsername());
        if(!isPresent.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getAdmin_Passcode().equals("546258")) {
                user.setRole("ADMIN");
            }
            else user.setRole("USER");
            userRepo.save(user);
            return user;
        }
        try {
            throw new Exception("UserName already Present Please try again !!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Find by UserName
    public User findUserByUserName (String userName) {
        Optional<User> isPresent = userRepo.findByUserName(userName);
        if(isPresent.isPresent()) {
            return isPresent.get();
        }
        return null;
    }

    //Delete user by Id
    public String deleteUserById(Long userId) {
        this.userRepo.deleteById(userId);
        Optional<User> byId = userRepo.findById(userId);
        if(byId.isEmpty()) {
            return "UserDeleted";
        }
        else return "UserNotDeleted";
    }
}
