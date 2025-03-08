package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {
    @Autowired
    private AirlineRepository airlineRepository;

    public List<Airline> getAllAirlines() {
        return airlineRepository.findAllAirline();
    }
}
