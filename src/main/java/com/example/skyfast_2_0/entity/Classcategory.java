package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classcategory")
public class Classcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Size(max = 255)
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "surcharge", nullable = false)
    private Float surcharge;

    // Getter and Setter methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Float surcharge) {
        this.surcharge = surcharge;
    }
}