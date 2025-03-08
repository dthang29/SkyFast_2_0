package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface L_AirlineRepository extends JpaRepository<Airline, Integer> {
 List<Airline> findByStatus(Status status);
 Optional<Airline> findById(Integer id);
}