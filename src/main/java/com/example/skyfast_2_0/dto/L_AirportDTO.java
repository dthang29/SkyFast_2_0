 package com.example.skyfast_2_0.dto;

 import lombok.Data;

 @Data
 public class L_AirportDTO {
     private Integer id;
     private String airportCode;
     private String airportName;
     private String country;
     private String location;
     private Integer runwaysCount;
     private String airportType;
     private Integer capacity;
     private String image;
     private String airportStatus = "ACTIVE";
 }