package com.example.skyfast_2_0.dto;


import com.example.skyfast_2_0.entity.Ticket;

public class K_TicketInfoDTO {
    private Integer id;
    private String status;
    private Float ticketPrice;
    private Integer bookingId;
    private String flightNumber;
    private String seatNumber;
    private String passengerFullName;

    public K_TicketInfoDTO() {
    }
    public K_TicketInfoDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.status = ticket.getStatus();
        this.ticketPrice = ticket.getTicketPrice();
        this.bookingId = ticket.getBooking().getId();
        this.flightNumber = ticket.getFlight().getFlightNumber();
        this.seatNumber = ticket.getSeat().getSeatNumber();
        this.passengerFullName = ticket.getPassenger().getFullName();
    }
    // Getters & Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Float getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(Float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getBookingId() {
        return bookingId;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPassengerFullName() {
        return passengerFullName;
    }
    public void setPassengerFullName(String passengerFullName) {
        this.passengerFullName = passengerFullName;
    }
}
