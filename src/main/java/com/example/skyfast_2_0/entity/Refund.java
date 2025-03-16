package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Size(max = 255)
    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @Size(max = 255)
    @NotNull
    @Column(name = "bank_number", nullable = false)
    private String bankNumber;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    @NotNull
    @Column(name = "refund_price", nullable = false)
    private Float refundPrice;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Lob
    @Column(name = "response", nullable = false, columnDefinition = "TEXT")
    private String response;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}