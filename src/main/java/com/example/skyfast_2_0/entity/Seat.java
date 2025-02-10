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
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "SeatNumber", nullable = false)
    private Integer seatNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "Class", nullable = false)
    private String classField;

    @Size(max = 255)
    @NotNull
    @Column(name = "Status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AirplaneId", nullable = false)
    private Airplane airplane;

}