package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    boolean existsByAirportName(String airportName);
    boolean existsByAirportCode(String airportCode);
    Airport findByAirportName(String airportName);
}
