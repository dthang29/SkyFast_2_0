package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Promotion;
import com.example.skyfast_2_0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Modifying
    @Query("insert into Booking (totalPrice,bookingDate, bookingStatus, user, promotion, bookingCode) values (:totalPrice,:bookingDate, :bookingStatus, :user, :promotion, :bookingCode)")
    void insertBooking(@Param("totalPrice") Float totalPrice,
                       @Param("bookingDate") LocalDateTime bookingDate,
                       @Param("bookingStatus") String bookingStatus,
                       @Param("user") User user,
                       @Param("promotion") Promotion promotion,
                       @Param("bookingCode") String bookingCode);
    @Query("SELECT b FROM Booking b WHERE b.id = (SELECT MAX(b2.id) FROM Booking b2)")
    Booking findBookingWithMaxId();
}
