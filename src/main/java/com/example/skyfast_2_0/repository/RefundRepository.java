package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.dto.RefundDTO;
import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
    @Query("SELECT new com.example.skyfast_2_0.dto.RefundDTO(" +
            "r.id, r.booking.bookingCode, r.bank, r.bankNumber, " +
            "r.requestDate, r.refundDate, r.refundPrice, r.reason, " +
            "r.response, r.status) " +
            "FROM Refund r WHERE r.booking.user.id = :userId")
    List<RefundDTO> findRefundsByUserId(@Param("userId") Integer userId);


    List<Refund> id(Integer id);
    
    boolean existsByBooking(Booking booking);

    List<Refund> findAllByBooking_User_Id(Integer userId);
}
