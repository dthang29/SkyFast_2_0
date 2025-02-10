package com.example.skyfast_2_0.entity;

import com.example.skyfast_2_0.constant.RouteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "route_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "departure_airport_id", nullable = false)
    private Integer departureAirportId;

    @NotNull
    @Column(name = "arrival_airport_id", nullable = false)
    private Integer arrivalAirportId;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Integer distance;

    @NotNull
    @ColumnDefault("'Active'")
    @Enumerated(EnumType.STRING)
    @Column(name = "route_status", nullable = false)
    private RouteStatus routeStatus;

}