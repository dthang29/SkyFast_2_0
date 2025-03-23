package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface K_RouteRepository extends JpaRepository<Route, Integer> {
    @Query("SELECT r.id, " +
            "CONCAT(da.airportName, ' (', da.location, ')') AS departureAirportName, " +
            "CONCAT(aa.airportName, ' (', aa.location, ')') AS arrivalAirportName, " +
            "r.distance " +
            "FROM Route r " +
            "JOIN Airport da ON r.departureAirportId = da.id " +
            "JOIN Airport aa ON r.arrivalAirportId = aa.id")
    List<Object[]> findAllRoutesWithAirportNames();
    boolean existsByDepartureAirportIdAndArrivalAirportId(int departureAirportId, int arrivalAirportId);
}
