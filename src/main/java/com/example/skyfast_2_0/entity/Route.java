package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "departure_airport_id", nullable = false)
    private int departureAirportId;

    @Size(max = 255)
    @NotNull
    @Column(name = "arrival_airport_id", nullable = false)
    private int arrivalAirportId;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Integer distance;

}