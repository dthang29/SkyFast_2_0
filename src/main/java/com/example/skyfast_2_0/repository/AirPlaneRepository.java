package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirPlaneRepository extends JpaRepository<Airplane, Integer> {
    @Query("SELECT a FROM Airplane a")
    List<Airplane> findAllPlane();
    @Query ("SELECT a FROM Airplane a WHERE a.id = :id")
    Airplane findById(int id);
}
