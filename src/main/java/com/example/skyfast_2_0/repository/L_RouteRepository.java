package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface L_RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findByRouteStatus(String routeStatus);
    Optional<Route> findByIdAndRouteStatus(Integer id, String routeStatus);
}