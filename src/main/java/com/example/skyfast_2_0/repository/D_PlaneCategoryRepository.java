package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface D_PlaneCategoryRepository extends JpaRepository<Airplane, Integer> {
    @Query("SELECT a FROM Airplane a WHERE " +
            "(:name IS NULL OR :name = '' OR a.airplaneName LIKE %:name%) " +
            "AND (:status IS NULL OR :status = '' OR a.airplaneStatus = :status) " +
            "AND (:airlineName IS NULL OR :airlineName = '' OR a.airline.airlineName LIKE %:airlineName%)")
    List<Airplane> searchAirplanes(@Param("name") String name,
                                   @Param("status") String status,
                                   @Param("airlineName") String airlineName);


    boolean existsByAirplaneName(String airplaneName);
}

