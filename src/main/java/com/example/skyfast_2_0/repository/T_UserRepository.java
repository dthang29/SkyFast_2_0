package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface T_UserRepository extends JpaRepository<User, Integer> {
}
