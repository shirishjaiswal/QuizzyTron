package com.exam.service;

import com.exam.dto.LoginRequestDetails;
import com.exam.module.UserEntity;
import com.exam.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    //creating user
    public UserEntity createUser(UserEntity userEntity) {
        Optional<UserEntity> isPresent = userRepo.findByUserName(userEntity.getUserName());
        if (!isPresent.isPresent()) {
            userEntity.setPassword(passwordEncoder(userEntity.getPassword()));
            if (userEntity.getAdminPasscode().equals("OFFICIAL")) {
                userEntity.setRole("ADMIN");
            } else userEntity.setRole("USER");
            userRepo.save(userEntity);
            return userEntity;
        }
        try {
            throw new Exception("UserName already Present Please try again !!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Find by UserName
    public UserEntity findUserByUserName(String userName) {
        Optional<UserEntity> isPresent = userRepo.findByUserName(userName);
        if (isPresent.isPresent()) {
            return isPresent.get();
        }
        return null;
    }

    //Delete user by Id
    public String deleteUserById(Long userId) {
        this.userRepo.deleteById(userId);
        Optional<UserEntity> byId = userRepo.findById(userId);
        if (byId.isEmpty()) {
            return "UserDeleted";
        } else return "UserNotDeleted";
    }

    //login
    public String verify(LoginRequestDetails loginRequestDetails) {
        UserEntity userByUserNameEntity = findUserByUserName(loginRequestDetails.getUserName());
        if (userByUserNameEntity == null) {
            try {
                throw new Exception("User is Not Present");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String encodedPassword = passwordEncoder(loginRequestDetails.getPassword());
        boolean validPassword = encodedPassword.equals(userByUserNameEntity.getPassword());
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

    public void userRequestValidate(String token, String userName) {
        Map<String, Boolean> validRequest = isValidRequest(token, userName);
        try {
            if(!Boolean.TRUE.equals(validRequest.get("USER"))) {
                throw new Exception("User Not Found");
            }
        }
        catch (Exception e) {
            if(!Boolean.TRUE.equals(validRequest.get("ADMIN"))) {
                try {
                    throw new Exception("User Not Found");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void adminRequestValidate(String token, String userName) {
        Map<String, Boolean> validRequest = isValidRequest(token, userName);
        if(!Boolean.TRUE.equals(validRequest.get("ADMIN"))) {
            try {
                throw new Exception("User Not Found");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private Map<String, Boolean> isValidRequest(String token, String userName) {
        String tempToken = generateToken(userName);
        Map<String, Boolean> map = new HashMap<>();
        if (tempToken.equals(token)) {
            UserEntity byToken = userRepo.findByToken(token);
            if (byToken != null) {
                map.put(byToken.getRole(), true);
            }
        }
        return map;
    }

    public String adminOrUser(String userName) {
        return findUserByUserName(userName).getRole();
    }

    private String generateToken(String userName) {
        UUID uuid = UUID.nameUUIDFromBytes(userName.getBytes(StandardCharsets.UTF_8));
        String token = uuid.toString();
        return token;
    }
}
