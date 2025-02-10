package com.example.skyfast_2_0.entity;

import com.example.skyfast_2_0.constant.RouteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "DepartureAirportId", nullable = false)
    private String departureAirportId;

    @Size(max = 255)
    @NotNull
    @Column(name = "ArrivalAirportId", nullable = false)
    private String arrivalAirportId;

    @NotNull
    @Column(name = "Distance", nullable = false)
    private Integer distance;

    @NotNull
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "RouteStatus", nullable = false)
    private RouteStatus routeStatus;

}