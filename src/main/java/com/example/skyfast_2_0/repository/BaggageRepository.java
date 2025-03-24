package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Integer> {
    List<Baggage> findByAirlineId(Integer airlineId);
    Baggage getBaggageById(Integer id);
}
