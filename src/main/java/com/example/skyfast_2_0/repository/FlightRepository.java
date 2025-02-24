package com.example.skyfast_2_0.repository;


import com.example.skyfast_2_0.entity.Flight;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
@Query("SELECT f FROM Flight f WHERE f.departureTime BETWEEN :startOfDay AND :endOfDay")
    List<Flight> findFlightsByDepartureTimeRange(@NotNull LocalDateTime startOfDay, @NotNull LocalDateTime endOfDay);
}