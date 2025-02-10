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
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "TotalPrice", nullable = false)
    private Integer totalPrice;

    @NotNull
    @Column(name = "BookingDate", nullable = false)
    private LocalDate bookingDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "PaymentStatus", nullable = false)
    private String paymentStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private com.example.skyfast_2_0.entity.User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PaymentId", nullable = false)
    private com.example.skyfast_2_0.entity.Payment payment;

}