package com.example.skyfast_2_0.mapper;

import com.example.skyfast_2_0.dto.L_AirportDTO;
import com.example.skyfast_2_0.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class L_AirportMapper {

    public L_AirportDTO toDTO(Airport airport) {
        L_AirportDTO dto = new L_AirportDTO();
        dto.setId(airport.getId()); // Đảm bảo tên trường là id
        dto.setAirportCode(airport.getAirportCode());
        dto.setAirportName(airport.getAirportName());
        dto.setCountry(airport.getCountry());
        dto.setLocation(airport.getLocation());
//        dto.setRunwaysCount(airport.getRunwaysCount());
//        dto.setAirportType(airport.getAirportType());
//        dto.setCapacity(airport.getCapacity());
//        dto.setImage(airport.getImage());
//        dto.setAirportStatus(airport.getAirportStatus());
        return dto;
    }

    public Airport toEntity(L_AirportDTO dto) {
        Airport airport = new Airport();
        airport.setId(dto.getId()); // Đảm bảo tên trường là id
        airport.setAirportCode(dto.getAirportCode());
        airport.setAirportName(dto.getAirportName());
        airport.setCountry(dto.getCountry());
        airport.setLocation(dto.getLocation());
//        airport.setRunwaysCount(dto.getRunwaysCount());
//        airport.setAirportType(dto.getAirportType());
//        airport.setCapacity(dto.getCapacity());
//        airport.setImage(dto.getImage());
//        airport.setAirportStatus(dto.getAirportStatus());
        return airport;
    }
}