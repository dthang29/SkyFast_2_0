package com.example.skyfast_2_0.dto;

import com.example.skyfast_2_0.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class K_TicketDTO {
    private Integer id;
    private String status;
    private Float ticketPrice;
    private int bookingId;
    private int seatId;
    private int flightId;
    private int passengerId;

    // Constructor to map from Ticket entity to TicketDTO
    public K_TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.status = ticket.getStatus();
        this.ticketPrice = ticket.getTicketPrice();
        this.bookingId = ticket.getBooking().getId();
        this.seatId = ticket.getSeat().getId();
        this.flightId = ticket.getFlight().getId();
        this.passengerId = ticket.getPassenger().getId();
    }
}
