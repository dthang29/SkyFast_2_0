package com.example.skyfast_2_0.repository;


import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query("SELECT r.id, da.location AS departureAirportName, aa.location AS arrivalAirportName " +
            "FROM Route r " +
            "JOIN Airport da ON r.departureAirportId = da.id " +
            "JOIN Airport aa ON r.arrivalAirportId = aa.id")
    List<Object[]> findAllRoutesWithAirportNames();

    @Query("SELECT aa.location AS arrivalAirportName, COUNT(r.id) AS routeCount " +
            "FROM Route r " +
            "JOIN Airport aa ON r.arrivalAirportId = aa.id " +
            "GROUP BY aa.location " +
            "ORDER BY routeCount DESC")
    List<Object[]> findTopPopularArrivalAirports();
    @Query("SELECT r.id, da.location AS departureAirportName, aa.location AS arrivalAirportName, COUNT(f.id) AS flightCount " +
            "FROM Route r " +
            "JOIN Airport da ON r.departureAirportId = da.id " +
            "JOIN Airport aa ON r.arrivalAirportId = aa.id " +
            "JOIN Flight f ON f.route.id = r.id " +
            "GROUP BY r.id, da.location, aa.location " +
            "ORDER BY flightCount DESC")
    List<Object[]> findTopPopularRoutes();
    @Query("SELECT a.id " +
            "FROM Airport a " +"WHERE a.location = :departureAirport")
    Integer airportIdByDepartureAirport(String departureAirport);

    @Query("SELECT r from Route r")
    List<Route> findAll();

}
