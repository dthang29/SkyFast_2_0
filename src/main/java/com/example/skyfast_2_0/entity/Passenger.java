package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "Title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Size(max = 255)
    @NotNull
    @Column(name = "Nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "IdentificationNumber", nullable = false)
    private Integer identificationNumber;

    @NotNull
    @Column(name = "PhoneNumber", nullable = false)
    private Integer phoneNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "Email", nullable = false)
    private String email;

}