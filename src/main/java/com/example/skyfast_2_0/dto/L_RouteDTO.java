package com.example.skyfast_2_0.dto;

import lombok.Data;

@Data
public class L_RouteDTO {
    private Integer id;
    private Integer departureAirportId;
    private Integer arrivalAirportId;
    private Integer distance;
    private String routeStatus = "ACTIVE";
}