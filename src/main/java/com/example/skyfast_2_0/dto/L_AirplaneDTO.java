 package com.example.skyfast_2_0.dto;

 import lombok.Data;

 @Data
 public class L_AirplaneDTO {
     private Integer id;
     private String airplaneName;
     private String manufacturer;
     private Float speed;
     private Float totalLength;
     private Float wingspan;
     private Float height;
     private String airplaneStatus;
     private Integer seatCapacity;
     private String airplaneImage;
     private Integer airlineId;
 }