package com.exam.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    @Column(unique = true)
    private String userName;
    private String password;
    private String role;
    @Column(nullable = true)
    private String adminPasscode = "NA";
    private String token;
}
