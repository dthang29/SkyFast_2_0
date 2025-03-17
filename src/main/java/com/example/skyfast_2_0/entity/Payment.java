package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_type_id", nullable = false)
    private com.example.skyfast_2_0.entity.Paymenttype paymentType;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}