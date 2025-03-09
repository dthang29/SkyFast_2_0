package com.example.skyfast_2_0.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.skyfast_2_0.entity.Seat;
import java.util.Optional;

public interface K_SeatRepository extends JpaRepository<Seat, Integer> {
    Optional<Seat> findBySeatNumber(String seatNumber);
}