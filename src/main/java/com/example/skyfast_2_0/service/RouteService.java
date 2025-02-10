package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Object[]> getAllRoutesWithAirportNames() {
        return routeRepository.findAllRoutesWithAirportNames();
    }
}