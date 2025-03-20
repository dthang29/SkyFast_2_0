package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "airplane")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_name", nullable = false)
    private String airplaneName;

    @Size(max = 255)
    @NotNull
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Float speed;

    @NotNull
    @Column(name = "total_length", nullable = false)
    private Float totalLength;

    @NotNull
    @Column(name = "wingspan", nullable = false)
    private Float wingspan;

    @NotNull
    @Column(name = "height", nullable = false)
    private Float height;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_status", nullable = false)
    private String airplaneStatus;

    @NotNull
    @Column(name = "seat_capacity", nullable = false)
    private Integer seatCapacity;

    @Size(max = 255)
    @NotNull
    @Column(name = "airplane_image", nullable = false)
    private String airplaneImage;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    // Getter and Setter for id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter and Setter for airplaneName
    public String getAirplaneName() {
        return airplaneName;
    }

    public void setAirplaneName(String airplaneName) {
        this.airplaneName = airplaneName;
    }

    // Getter and Setter for manufacturer
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    // Getter and Setter for speed
    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    // Getter and Setter for totalLength
    public Float getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Float totalLength) {
        this.totalLength = totalLength;
    }

    // Getter and Setter for wingspan
    public Float getWingspan() {
        return wingspan;
    }

    public void setWingspan(Float wingspan) {
        this.wingspan = wingspan;
    }

    // Getter and Setter for height
    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) { this.height = height;  }

    // Getter and Setter for airplaneStatus
    public String getAirplaneStatus() { return airplaneStatus; }

    public void setAirplaneStatus(String airplaneStatus) {
        this.airplaneStatus = airplaneStatus;
    }

    // Getter and Setter for seatCapacity
    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    // Getter and Setter for airplaneImage
    public String getAirplaneImage() {
        return airplaneImage;
    }

    public void setAirplaneImage(String airplaneImage) {
        this.airplaneImage = airplaneImage;
    }

    // Getter and Setter for airline
    public Airline getAirline() { return airline; }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}