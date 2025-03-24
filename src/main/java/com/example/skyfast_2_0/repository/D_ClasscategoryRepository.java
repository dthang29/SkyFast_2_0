package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Classcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface D_ClasscategoryRepository extends JpaRepository<Classcategory, Integer> {
    List<Classcategory> findByAirplane(Airplane airplane);
    boolean existsByAirplaneAndName(Airplane airplane, String name);
}
