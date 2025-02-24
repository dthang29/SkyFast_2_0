package com.example.skyfast_2_0.repository;


import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Airport, Integer> {
    @Query("SELECT r.id, da.location AS departureAirportName, aa.location AS arrivalAirportName " +
            "FROM Route r " +
            "JOIN Airport da ON r.departureAirportId = da.id " +
            "JOIN Airport aa ON r.arrivalAirportId = aa.id")
    List<Object[]> findAllRoutesWithAirportNames();
}
