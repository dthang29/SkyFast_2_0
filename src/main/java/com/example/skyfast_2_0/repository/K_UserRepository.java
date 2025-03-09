package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface K_UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUserName(String userName);
}
