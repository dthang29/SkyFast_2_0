package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Flight;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface K_FlightRepository extends JpaRepository<Flight, Integer> {
   Optional<Flight> findByFlightNumber(String flightNumber);
}
