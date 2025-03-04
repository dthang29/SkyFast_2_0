package com.example.skyfast_2_0.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineDTO {
    private Integer id;
    private String airlineName;
    private String countryName;
    private Date foundedDate;
    private String image;
}