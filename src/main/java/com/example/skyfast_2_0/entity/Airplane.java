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
    @Column(name = "airplane_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_name", nullable = false)
    private String airplaneName;

    @NotNull
    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @Size(max = 255)
    @NotNull
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Size(max = 255)
    @NotNull
    @Column(name = "diagram", nullable = false)
    private String diagram;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Integer speed;

    @NotNull
    @Column(name = "total_length", nullable = false)
    private Float totalLength;

    @NotNull
    @Column(name = "wingspan", nullable = false)
    private Float wingspan;

    @NotNull
    @Column(name = "height", nullable = false)
    private Float height;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

}