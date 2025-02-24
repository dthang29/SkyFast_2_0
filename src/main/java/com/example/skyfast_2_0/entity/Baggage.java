package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "baggage")
public class Baggage {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Size(max = 255)
    @NotNull
    @Column(name = "baggage_status", nullable = false)
    private String baggageStatus;

    @NotNull
    @Column(name = "baggage_price", nullable = false)
    private Integer baggagePrice;

    @NotNull
    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    private com.example.skyfast_2_0.entity.Passenger passenger;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;
}
