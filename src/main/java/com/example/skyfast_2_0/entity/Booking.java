package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.example.skyfast_2_0.entity.User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private com.example.skyfast_2_0.entity.Promotion promotion;

    @Size(max = 255)
    @NotNull
    @Column(name = "booking_code", nullable = false)
    private String bookingCode;

    // Getter và Setter cho id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter và Setter cho totalPrice
    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter và Setter cho bookingDate
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    // Getter và Setter cho bookingStatus
    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Getter và Setter cho user
    public com.example.skyfast_2_0.entity.User getUser() {
        return user;
    }

    public void setUser(com.example.skyfast_2_0.entity.User user) {
        this.user = user;
    }

    // Getter và Setter cho promotion
    public com.example.skyfast_2_0.entity.Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(com.example.skyfast_2_0.entity.Promotion promotion) {
        this.promotion = promotion;
    }

    // Getter và Setter cho bookingCode
    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }
}