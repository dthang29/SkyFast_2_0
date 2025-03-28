package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface T_UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
    User findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNum);
}
