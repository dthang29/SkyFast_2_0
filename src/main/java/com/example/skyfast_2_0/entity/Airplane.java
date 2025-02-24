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
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_name", nullable = false)
    private String airplaneName;

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
    private Integer totalLength;

    @NotNull
    @Column(name = "wingspan", nullable = false)
    private Integer wingspan;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_status", nullable = false)
    private String airplaneStatus;

    @NotNull
    @Column(name = "seat_capacity", nullable = false)
    private Integer seatCapacity;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_image", nullable = false)
    private String airplaneImage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;
}
