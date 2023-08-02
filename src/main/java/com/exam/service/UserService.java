package com.exam.service;

import com.exam.dto.LoginRequestDetails;
import com.exam.module.User;
import com.exam.repository.IRoleRepo;
import com.exam.repository.IUserRepo;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IRoleRepo roleRepo;

    //creating user
    public User createUser(User user) {
        Optional<User> isPresent = userRepo.findByUserName(user.getUserName());
        if (!isPresent.isPresent()) {
            user.setPassword(passwordEncoder(user.getPassword()));
            if (user.getAdmin_Passcode().equals("546258")) {
                user.setRole("ADMIN");
            } else user.setRole("USER");
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
    public User findUserByUserName(String userName) {
        Optional<User> isPresent = userRepo.findByUserName(userName);
        if (isPresent.isPresent()) {
            return isPresent.get();
        }
        return null;
    }

    //Delete user by Id
    public String deleteUserById(Long userId) {
        this.userRepo.deleteById(userId);
        Optional<User> byId = userRepo.findById(userId);
        if (byId.isEmpty()) {
            return "UserDeleted";
        } else return "UserNotDeleted";
    }

    //login
    public String verify(LoginRequestDetails loginRequestDetails) {
        User userByUserName = findUserByUserName(loginRequestDetails.getUserName());
        if (userByUserName == null) {
            try {
                throw new Exception("User is Not Present");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String encodedPassword = passwordEncoder(loginRequestDetails.getPassword());
        boolean validPassword = encodedPassword.equals(userByUserName.getPassword());
        if (validPassword) {
            String token = generateToken(loginRequestDetails.getUserName());
            userRepo.addToken(token, loginRequestDetails.getUserName());
            return token;
        } else {
            try {
                throw new Exception("Invalid Password");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int logout(String token, String userName) {
        int i = userRepo.addToken(null, userName);
        return i;
    }

    private String passwordEncoder(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();

            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return password;
    }

    public boolean isValidRequest(String token, String userName) {
        String tempToken = generateToken(userName);
        if(tempToken.equals(token)) {
            User byToken = userRepo.findByToken(token);
            if (byToken != null) {
                return true;
            }
        }
        try {
            throw new Exception("Bad Request");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String generateToken(String userName) {
        UUID uuid = UUID.nameUUIDFromBytes(userName.getBytes(StandardCharsets.UTF_8));
        String token = uuid.toString();
        return token;
    }
}
