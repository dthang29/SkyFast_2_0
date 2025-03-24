package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface D_RefundRepository extends JpaRepository<Refund, Integer> {

    @Query("SELECT r FROM Refund r WHERE " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:fromRequestDate IS NULL OR r.requestDate >= :fromRequestDate) AND " +
            "(:toRequestDate IS NULL OR r.requestDate <= :toRequestDate) AND " +
            "(:fromRefundDate IS NULL OR r.refundDate >= :fromRefundDate) AND " +
            "(:toRefundDate IS NULL OR r.refundDate <= :toRefundDate)")
    List<Refund> searchRefunds(
            @Param("status") String status,
            @Param("fromRequestDate") LocalDateTime fromRequestDate,
            @Param("toRequestDate") LocalDateTime toRequestDate,
            @Param("fromRefundDate") LocalDateTime fromRefundDate,
            @Param("toRefundDate") LocalDateTime toRefundDate);
}
