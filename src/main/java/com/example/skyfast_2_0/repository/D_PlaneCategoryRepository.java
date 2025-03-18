package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface D_PlaneCategoryRepository extends JpaRepository<Airplane, Integer> {
    @Query("SELECT a FROM Airplane a WHERE " +
            "(:name IS NULL OR :name = '' OR a.airplaneName LIKE %:name%) " +
            "AND (:status IS NULL OR :status = '' OR a.airplaneStatus = :status)")
    List<Airplane> searchAirplanes(@Param("name") String name, @Param("status") String status);


    boolean existsByAirplaneName(String airplaneName);
}

