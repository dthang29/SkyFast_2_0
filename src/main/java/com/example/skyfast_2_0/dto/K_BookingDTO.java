package com.example.skyfast_2_0.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class K_BookingDTO {
    // Getters and Setters
    private Integer id;
    private Float totalPrice;
    private LocalDate bookingDate;
    private String bookingStatus;
    private Integer userId;
    private String status;
    private String userName;
    // Constructor with parameters
    public K_BookingDTO(Integer id, Float totalPrice, LocalDate bookingDate, String bookingStatus, Integer userId, String userName) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.userId = userId;
        this.userName = userName;
    }

    // Default constructor
    public K_BookingDTO() {
    }

}