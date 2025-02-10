package com.example.skyfast_2_0.entity;

import com.example.skyfast_2_0.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "UserName", nullable = false)
    private String userName;

    @Size(max = 255)
    @NotNull
    @Column(name = "Password", nullable = false)
    private String password;

    @Size(max = 255)
    @NotNull
    @Column(name = "Email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Column(name = "PhoneNumber")
    private Integer phoneNumber;

    @Size(max = 255)
    @Column(name = "Address")
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdateAt")
    private LocalDateTime updateAt;

}