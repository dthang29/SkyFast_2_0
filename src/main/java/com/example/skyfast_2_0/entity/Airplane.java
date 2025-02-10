package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "airplane")
public class Airplane {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "AirplaneName", nullable = false)
    private String airplaneName;

    @NotNull
    @Column(name = "SeatingCapacity", nullable = false)
    private Integer seatingCapacity;

    @Size(max = 255)
    @NotNull
    @Column(name = "Manufacturer", nullable = false)
    private String manufacturer;

    @Size(max = 255)
    @NotNull
    @Column(name = "Diagram", nullable = false)
    private String diagram;

    @NotNull
    @Column(name = "Speed", nullable = false)
    private Integer speed;

    @NotNull
    @Column(name = "TotalLength", nullable = false)
    private Float totalLength;

    @NotNull
    @Column(name = "Wingspan", nullable = false)
    private Float wingspan;

    @NotNull
    @Column(name = "Height", nullable = false)
    private Float height;

    @Size(max = 255)
    @NotNull
    @Column(name = "Status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AirlineId", nullable = false)
    private Airline airline;

}