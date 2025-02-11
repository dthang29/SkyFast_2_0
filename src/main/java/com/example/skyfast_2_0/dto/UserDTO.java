package com.example.skyfast_2_0.dto;

import com.example.skyfast_2_0.constant.Role;
import lombok.*;

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
    private Integer phoneNumber;
    private String address;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
