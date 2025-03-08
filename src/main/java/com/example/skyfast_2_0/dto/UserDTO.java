package com.example.skyfast_2_0.dto;

import com.example.skyfast_2_0.constant.Role;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private Integer id;
    private String userName;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
    private LocalDate dateOfBirth;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
