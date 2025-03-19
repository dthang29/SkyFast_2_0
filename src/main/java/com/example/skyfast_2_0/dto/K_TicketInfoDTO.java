package com.example.skyfast_2_0.dto;

import com.example.skyfast_2_0.entity.Ticket;

public class K_TicketInfoDTO {
    private Integer id;
    private String status;
    private Integer bookingId;
    private String flightNumber;
    private String passengerFullName;
    private String seatCode;
    private Integer classCategoryId;

    public K_TicketInfoDTO() {
    }

    public K_TicketInfoDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.status = ticket.getStatus();
        this.bookingId = ticket.getBooking().getId();
        this.flightNumber = ticket.getFlight().getFlightNumber();
        this.passengerFullName = ticket.getPassenger().getFullName();
        this.seatCode = ticket.getSeatCode();
        this.classCategoryId = ticket.getClassCategory().getId();
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

    public String getPassengerFullName() {
        return passengerFullName;
    }
    public void setPassengerFullName(String passengerFullName) {
        this.passengerFullName = passengerFullName;
    }

    public String getSeatCode() {return seatCode;}
    public void setSeatCode(String seatCode) {this.seatCode = seatCode;}

    public Integer getClassCategoryId() {return classCategoryId;}
    public void setClassCategoryId(Integer classCategoryId) {this.classCategoryId = classCategoryId;}
}
