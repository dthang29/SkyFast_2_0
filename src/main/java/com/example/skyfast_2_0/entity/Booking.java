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
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.example.skyfast_2_0.entity.User user;

    // === CONSTRUCTORS ===
    public Booking() {
    }

    public Booking(Integer id, Float totalPrice, LocalDate bookingDate, String bookingStatus, User user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.user = user;
    }

    // === GETTERS ===
    public Integer getId() {
        return id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public User getUser() {
        return user;
    }

    // === SETTERS ===
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // === toString() ===
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", bookingDate=" + bookingDate +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", user=" + (user != null ? user.getId() : "null") +
                '}';
    }
}