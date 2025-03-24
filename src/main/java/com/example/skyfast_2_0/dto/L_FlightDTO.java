package com.example.skyfast_2_0.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class L_FlightDTO {
    private Integer id;
    private String flightNumber;

    @NotNull(message = "Departure time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;

    private Integer duration;
    private Integer price;
    private String status;
    private Integer airlineId;
    private Integer airplaneId;
    private Integer routeId;
    private String statusFlight = "ACTIVE";

}