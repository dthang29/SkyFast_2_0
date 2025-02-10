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
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "Weight", nullable = false)
    private Integer weight;

    @Size(max = 255)
    @NotNull
    @Column(name = "Type", nullable = false)
    private String type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TicketId", nullable = false)
    private com.example.skyfast_2_0.entity.Ticket ticket;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PassengerId", nullable = false)
    private com.example.skyfast_2_0.entity.Passenger passenger;

}