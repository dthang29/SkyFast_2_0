package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    boolean existsByAirportName(String airportName);
    boolean existsByAirportCode(String airportCode);
    Airport findByAirportName(String airportName);
    @Query("SELECT a FROM Airport a")
    List<Airport> findAllAirport();
}
