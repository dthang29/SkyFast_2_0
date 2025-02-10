package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "route_airport")
public class RouteAirport {
    @EmbeddedId
    private RouteAirportId id;

    @MapsId("airportId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AirportId", nullable = false)
    private Airport airport;

    @MapsId("routeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RouteId", nullable = false)
    private Route route;

}