package com.example.skyfast_2_0.mapper;

import com.example.skyfast_2_0.dto.L_FlightDTO;
import com.example.skyfast_2_0.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class L_FlightMapper {

    public L_FlightDTO toDTO(Flight flight) {
        L_FlightDTO dto = new L_FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime()); // Đã là Timestamp
        dto.setArrivalTime(flight.getArrivalTime());     // Đã là Timestamp
        dto.setDuration(flight.getDuration());
        dto.setStatus(flight.getStatus());
        dto.setAirlineId(flight.getAirline().getId());
        dto.setAirplaneId(flight.getAirplane().getId());
        dto.setRouteId(flight.getRoute().getId());
        dto.setPrice(flight.getPrice());                 // Thêm price
        dto.setStatusFlight(flight.getStatusFlight());
        return dto;
    }

    public Flight toEntity(L_FlightDTO dto) {
        Flight flight = new Flight();
        flight.setId(dto.getId());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime()); // Đã là Timestamp
        flight.setArrivalTime(dto.getArrivalTime());     // Đã là Timestamp
        flight.setDuration(dto.getDuration());
        flight.setStatus(dto.getStatus());
        flight.setPrice(dto.getPrice());                 // Thêm price
        flight.setStatusFlight(dto.getStatusFlight());
        return flight;
    }
}