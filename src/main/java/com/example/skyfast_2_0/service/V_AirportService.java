package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.repository.V_AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class V_AirportService {
    @Autowired
    private V_AirportRepository VAirportRepository;
    public Airport findAirportById(Integer id) {
        return VAirportRepository.findAirportById(id);
    }
}
