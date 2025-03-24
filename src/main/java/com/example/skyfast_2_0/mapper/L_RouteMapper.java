package com.example.skyfast_2_0.mapper;

import com.example.skyfast_2_0.dto.L_RouteDTO;
import com.example.skyfast_2_0.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class L_RouteMapper {

    public L_RouteDTO toDTO(Route route) {
        L_RouteDTO dto = new L_RouteDTO();
        dto.setId(route.getId());
        dto.setDepartureAirportId((int) route.getDepartureAirport().getId());
        dto.setArrivalAirportId((int) route.getArrivalAirport().getId());
        dto.setDistance(route.getDistance());
        dto.setRouteStatus(route.getRouteStatus());
        return dto;
    }

    public Route toEntity(L_RouteDTO dto) {
        Route route = new Route();
        route.setId(dto.getId());
        route.setDistance(dto.getDistance());
        route.setRouteStatus(dto.getRouteStatus());
        return route;
    }
}