package com.example.skyfast_2_0.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private String status;
    private Float ticketPrice;
    private int bookingId;
    private int seatId;
    private int flightId;
    private int passengerId;
}
