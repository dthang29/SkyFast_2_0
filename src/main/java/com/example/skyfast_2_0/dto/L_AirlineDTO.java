package com.example.skyfast_2_0.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class L_AirlineDTO {
    private Integer id;
    private String airlineName;
    private String countryName;
    private LocalDate foundedDate;
    private String image;
}