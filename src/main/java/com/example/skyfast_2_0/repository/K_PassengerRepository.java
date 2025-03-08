package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface K_PassengerRepository extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByFullName(String fullName);
}
