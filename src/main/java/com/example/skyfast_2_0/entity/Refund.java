package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @NotNull
    @Column(name = "bank_number", nullable = false)
    private Integer bankNumber;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @NotNull
    @Column(name = "refund_date", nullable = false)
    private LocalDate refundDate;

    @NotNull
    @Column(name = "refund_price", nullable = false)
    private Float refundPrice;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}