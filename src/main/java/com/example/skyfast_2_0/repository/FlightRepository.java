package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Flight;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
@Query("SELECT f FROM Flight f WHERE f.departureTime BETWEEN :startOfDay AND :endOfDay")
    List<Flight> findFlightsByDepartureTimeRange(@NotNull LocalDateTime startOfDay, @NotNull LocalDateTime endOfDay);
    @Query("""
    SELECT f FROM Flight f
    JOIN f.route r
    JOIN f.airplane a
    JOIN Seat s ON s.airplane.id = a.id
    JOIN s.classcategory c
    WHERE (:departureAirportId IS NULL OR r.departureAirportId = :departureAirportId)
    AND (:arrivalAirportId IS NULL OR r.arrivalAirportId = :arrivalAirportId)
    AND (function('DATE', f.departureTime) BETWEEN :departureDateStart AND :departureDateEnd)
    AND (:classCategoryId IS NULL OR c.id = :classCategoryId)
    GROUP BY f.id
    HAVING SUM(CASE WHEN s.status = 'available' THEN 1 ELSE 0 END) >= :passengerCount OR :passengerCount IS NULL
""")
    List<Flight> searchFlights(
            @Param("departureAirportId") Integer departureAirportId,
            @Param("arrivalAirportId") Integer arrivalAirportId,
            @Param("departureDateStart") LocalDateTime departureDateStart,
            @Param("departureDateEnd") LocalDateTime departureDateEnd,
            @Param("classCategoryId") Integer classCategoryId,
            @Param("passengerCount") Integer passengerCount
    );
}