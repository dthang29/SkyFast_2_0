package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "seat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "class", nullable = false)
    private String classField;

    @Size(max = 255)
    @NotNull
    @Column(name = "seat_status", nullable = false)
    private String seatStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

}