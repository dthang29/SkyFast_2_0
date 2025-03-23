package com.example.skyfast_2_0.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportDTO {
    private int id;
    private String airport_name;
    private String airport_code;
    private String country;
    private String location;

    public AirportDTO(int id, String airport_name, String airport_code, String country, String location) {
        this.id = id;
        this.airport_name = airport_name;
        this.airport_code = airport_code;
        this.country = country;
        this.location = location;
    }

    public AirportDTO() {}
}
