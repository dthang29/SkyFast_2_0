package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "FlightNumber", nullable = false)
    private String flightNumber;

    @NotNull
    @Column(name = "DepartureTime", nullable = false)
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "ArrivalTime", nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(name = "Duration", nullable = false)
    private Float duration;

    @Size(max = 255)
    @NotNull
    @Column(name = "Status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AirlineId", nullable = false)
    private Airline airline;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AirplaneId", nullable = false)
    private Airplane airplane;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RouteId", nullable = false)
    private com.example.skyfast_2_0.entity.Route route;

}