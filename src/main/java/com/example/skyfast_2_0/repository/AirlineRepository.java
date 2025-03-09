package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
@Query("SELECT a FROM Airline a")
List<Airline> findAllAirline();
}
