package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Classcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassCategoryRepository extends JpaRepository<Classcategory, Integer> {
    @Query("SELECT c FROM Classcategory c WHERE c.id IN (SELECT MIN(c2.id) FROM Classcategory c2 GROUP BY c2.name)")
    List<Classcategory> findAllClassCategories();
    List<Classcategory> findByAirplaneId(Integer airplaneId);
}
