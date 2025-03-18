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

    @Query("SELECT f FROM Flight f " +
            "WHERE f.price = (SELECT MIN(f2.price) FROM Flight f2 WHERE f2.route.id = f.route.id) " +
            "ORDER BY f.price ASC")
    List<Flight> findTopCheapestFlightsByRoute();

    @Query("""
    SELECT f FROM Flight f
    JOIN f.route r
    JOIN f.airplane a
    JOIN Classcategory c ON a.id = c.airplane.id
    WHERE (:departureAirportId IS NULL OR r.departureAirportId = :departureAirportId)
    AND (:arrivalAirportId IS NULL OR r.arrivalAirportId = :arrivalAirportId)
    AND (function('DATE', f.departureTime) BETWEEN :departureDateStart AND :departureDateEnd)
    AND (:classCategoryName IS NULL OR c.name = :classCategoryName)
    GROUP BY f.id
""")
    List<Flight> searchFlights(
            @Param("departureAirportId") Integer departureAirportId,
            @Param("arrivalAirportId") Integer arrivalAirportId,
            @Param("departureDateStart") LocalDateTime departureDateStart,
            @Param("departureDateEnd") LocalDateTime departureDateEnd,
            @Param("classCategoryName") String classCategoryName
//            @Param("passengerCount") Integer passengerCount
    );
}
