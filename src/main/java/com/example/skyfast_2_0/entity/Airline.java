package com.example.skyfast_2_0.entity;

import com.example.skyfast_2_0.constant.Status;
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
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "airline_name", nullable = false)
    private String airlineName;

    @Size(max = 255)
    @NotNull
    @Column(name = "country_of_operation", nullable = false)
    private String countryOfOperation;

    @NotNull
    @Column(name = "founded_date", nullable = false)
    private LocalDate foundedDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;
}