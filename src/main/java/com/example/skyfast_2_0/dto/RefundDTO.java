package com.example.skyfast_2_0.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RefundDTO {
    private Integer id;
    private String bookingCode;
    private String bank;
    private String bankNumber;
    private LocalDateTime requestDate;
    private LocalDateTime refundDate;
    private Float refundPrice;
    private String reason;
    private String response;
    private String status;


    public RefundDTO(Integer id, String bookingCode, String bank, String bankNumber, LocalDateTime requestDate, LocalDateTime refundDate, Float refundPrice, String reason, String response, String status) {
        this.id = id;
        this.bookingCode = bookingCode;
        this.bank = bank;
        this.bankNumber = bankNumber;
        this.requestDate = requestDate;
        this.refundDate = refundDate;
        this.refundPrice = refundPrice;
        this.reason = reason;
        this.response = response;
        this.status = status;

    }

    public RefundDTO() {
    }

}
