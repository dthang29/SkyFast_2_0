package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "route")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "departure_airport_id", nullable = false)
    private Integer departureAirportId;

    @NotNull
    @Column(name = "arrival_airport_id", nullable = false)
    private Integer arrivalAirportId;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Integer distance;

    @Size(max = 255)
    @NotNull
    @Column(name = "route_status", nullable = false)
    private String routeStatus;
}