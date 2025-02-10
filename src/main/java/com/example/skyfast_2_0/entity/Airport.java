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
    @Column(name = "airport_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "airport_code", nullable = false, length = 10)
    private String airportCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "airport_name", nullable = false)
    private String airportName;

    @Size(max = 255)
    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @Size(max = 255)
    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "runways_count", nullable = false)
    private Integer runwaysCount;

    @Size(max = 255)
    @NotNull
    @Column(name = "airport_type", nullable = false)
    private String airportType;

}