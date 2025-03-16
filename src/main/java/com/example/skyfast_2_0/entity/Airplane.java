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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_name", nullable = false)
    private String airplaneName;

    @Size(max = 255)
    @NotNull
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Float speed;

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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

}