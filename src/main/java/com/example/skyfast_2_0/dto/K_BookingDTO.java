package com.example.skyfast_2_0.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class K_BookingDTO {
    // Getters and Setters
    private Integer id;
    private Float totalPrice;
    private LocalDateTime bookingDate;
    private String bookingStatus;
    private Integer userId;
    private String status;
    private String userName;
    private Integer promotionId;
    private String code;
    private String bookingCode;

    // Constructor with parameters
    public K_BookingDTO(Integer id, Float totalPrice,
                        LocalDateTime bookingDate, String bookingStatus,
                        Integer userId, String userName, Integer promotionId,
                        String code,  String bookingCode) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.userId = userId;
        this.userName = userName;
        this.promotionId = promotionId;
        this.code = code;
        this.bookingCode = bookingCode;
    }

    // Default constructor
    public K_BookingDTO() {
    }

}