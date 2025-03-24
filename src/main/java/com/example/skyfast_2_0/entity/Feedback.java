package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Lob
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @NotNull
    @Column(name = "feedback_date", nullable = false)
    private LocalDate feedbackDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.example.skyfast_2_0.entity.User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private com.example.skyfast_2_0.entity.Flight flight;

    // Getter và Setter cho id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter và Setter cho rating
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    // Getter và Setter cho comments
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // Getter và Setter cho feedbackDate
    public LocalDate getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    // Getter và Setter cho user
    public com.example.skyfast_2_0.entity.User getUser() {
        return user;
    }

    public void setUser(com.example.skyfast_2_0.entity.User user) {
        this.user = user;
    }

    // Getter và Setter cho flight
    public com.example.skyfast_2_0.entity.Flight getFlight() {
        return flight;
    }

    public void setFlight(com.example.skyfast_2_0.entity.Flight flight) {
        this.flight = flight;
    }
}
