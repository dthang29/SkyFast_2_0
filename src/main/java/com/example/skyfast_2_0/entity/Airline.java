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
@Table(name = "airline")
public class Airline {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "AirlineName", nullable = false)
    private String airlineName;

    @Size(max = 255)
    @NotNull
    @Column(name = "Country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "FoundedDate", nullable = false)
    private LocalDate foundedDate;

    @NotNull
    @Column(name = "FleetSize", nullable = false)
    private Integer fleetSize;

}