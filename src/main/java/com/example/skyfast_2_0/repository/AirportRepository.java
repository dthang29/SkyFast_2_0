package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Query("SELECT a from Airport a where a.id=:id")
    Airport findAirportById(@Param("id") Integer id);
}
