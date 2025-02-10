package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "AirportCode", nullable = false, length = 10)
    private String airportCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "AirportName", nullable = false)
    private String airportName;

    @Size(max = 255)
    @NotNull
    @Column(name = "Country", nullable = false)
    private String country;

    @Size(max = 255)
    @NotNull
    @Column(name = "Location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "RunwaysCount", nullable = false)
    private Integer runwaysCount;

    @Size(max = 255)
    @NotNull
    @Column(name = "AirportType", nullable = false)
    private String airportType;

}