package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Integer> {

    List<Refund> id(Integer id);
    
    boolean existsByBooking(Booking booking);

    List<Refund> findAllByBooking_User_Id(Integer userId);
}
