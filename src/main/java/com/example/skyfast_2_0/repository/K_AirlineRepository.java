package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface K_AirlineRepository extends JpaRepository<Airline, Integer> {
    Optional<Airline> findByAirlineName(String airlineName);
}
