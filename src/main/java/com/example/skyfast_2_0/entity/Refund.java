package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @Size(max = 255)
    @NotNull
    @Column(name = "bank_number", nullable = false)
    private String bankNumber;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @NotNull
    @Column(name = "refund_date", nullable = false)
    private LocalDate refundDate;

    @NotNull
    @Column(name = "refund_price", nullable = false)
    private Float refundPrice;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // === CONSTRUCTORS ===
    public Refund() {
    }

    public Refund(Integer id, String bank, String bankNumber, LocalDate requestDate, LocalDate refundDate, Float refundPrice, String status, Booking booking) {
        this.id = id;
        this.bank = bank;
        this.bankNumber = bankNumber;
        this.requestDate = requestDate;
        this.refundDate = refundDate;
        this.refundPrice = refundPrice;
        this.status = status;
        this.booking = booking;
    }

    // === GETTERS ===
    public Integer getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public Float getRefundPrice() {
        return refundPrice;
    }

    public String getStatus() {
        return status;
    }

    public Booking getBooking() {
        return booking;
    }

    // === SETTERS ===
    public void setId(Integer id) {
        this.id = id;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public void setRefundPrice(Float refundPrice) {
        this.refundPrice = refundPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    // === toString() ===
    @Override
    public String toString() {
        return "Refund{" +
                "id=" + id +
                ", bank='" + bank + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", requestDate=" + requestDate +
                ", refundDate=" + refundDate +
                ", refundPrice=" + refundPrice +
                ", status='" + status + '\'' +
                ", booking=" + (booking != null ? booking.getId() : "null") +
                '}';
    }
}