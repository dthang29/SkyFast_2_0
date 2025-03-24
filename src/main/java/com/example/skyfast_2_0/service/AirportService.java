package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.repository.AirportRepository;
import com.example.skyfast_2_0.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;
    public Airport findAirportById(Integer id) {
        return airportRepository.findAirportById(id);
    }
}
