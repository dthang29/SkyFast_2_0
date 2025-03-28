package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface K_BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByTotalPrice(Float totalPrice);
    Optional<Booking> findByBookingCode(String bookingCode);
}
