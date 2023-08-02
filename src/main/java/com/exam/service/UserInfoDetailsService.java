package com.exam.service;

import com.exam.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if(user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("Invalid Credentials");
        }
        return user;
    }
}
