package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface L_AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByAirportStatus(String airportStatus);
    Optional<Airport> findByIdAndAirportStatus(Integer id, String airportStatus); // Sử dụng 'id' thay vì 'airportId'
}