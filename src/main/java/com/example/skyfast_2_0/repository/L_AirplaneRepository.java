 package com.example.skyfast_2_0.repository;

 import com.example.skyfast_2_0.entity.Airplane;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;
 import java.util.Optional;

 @Repository
 public interface L_AirplaneRepository extends JpaRepository<Airplane, Integer> {
     List<Airplane> findByAirplaneStatus(String status);
     Optional<Airplane> findByIdAndAirplaneStatus(Integer id, String status);
 }