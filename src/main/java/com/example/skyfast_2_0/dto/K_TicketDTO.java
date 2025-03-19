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
    private int bookingId;
    private int flightId;
    private int passengerId;
    private String seatCode;
    private int classCategoryId;


    // Constructor to map from Ticket entity to TicketDTO
    public K_TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.status = ticket.getStatus();
        this.bookingId = ticket.getBooking().getId();
        this.flightId = ticket.getFlight().getId();
        this.passengerId = ticket.getPassenger().getId();
        this.seatCode = ticket.getSeatCode();
        this.classCategoryId = ticket.getClassCategory().getId();
    }
}
