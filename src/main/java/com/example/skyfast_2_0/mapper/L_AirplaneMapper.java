package com.example.skyfast_2_0.mapper;

import com.example.skyfast_2_0.dto.L_AirplaneDTO;
import com.example.skyfast_2_0.entity.Airplane;
import org.springframework.stereotype.Component;

@Component
public class L_AirplaneMapper {

    public L_AirplaneDTO toDTO(Airplane airplane) {
        L_AirplaneDTO dto = new L_AirplaneDTO();
        dto.setId(airplane.getId());
        dto.setAirplaneName(airplane.getAirplaneName());
        dto.setManufacturer(airplane.getManufacturer());
//        dto.setDiagram(airplane.getDiagram());
        dto.setSpeed(airplane.getSpeed());
        dto.setTotalLength(airplane.getTotalLength());
        dto.setWingspan(airplane.getWingspan());
        dto.setHeight(airplane.getHeight());
        dto.setAirplaneStatus(airplane.getAirplaneStatus());
        dto.setSeatCapacity(airplane.getSeatCapacity());
        dto.setAirplaneImage(airplane.getAirplaneImage());
        dto.setAirlineId(airplane.getAirline().getId());
        return dto;
    }

    public Airplane toEntity(L_AirplaneDTO dto) {
        Airplane airplane = new Airplane();
        airplane.setId(dto.getId());
        airplane.setAirplaneName(dto.getAirplaneName());
        airplane.setManufacturer(dto.getManufacturer());
//        airplane.setDiagram(dto.getDiagram());
        airplane.setSpeed(dto.getSpeed());
        airplane.setTotalLength(dto.getTotalLength());
        airplane.setWingspan(dto.getWingspan());
        airplane.setHeight(dto.getHeight());
        airplane.setAirplaneStatus(dto.getAirplaneStatus());
        airplane.setSeatCapacity(dto.getSeatCapacity());
        airplane.setAirplaneImage(dto.getAirplaneImage());
        return airplane;
    }
}