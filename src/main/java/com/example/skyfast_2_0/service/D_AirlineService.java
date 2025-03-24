package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.repository.D_AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class D_AirlineService {

    @Autowired
    private D_AirlineRepository DAirlineRepository;

    public List<Airline> getAllAirlines() {
        return DAirlineRepository.findAll();
    }

    public Airline findById(Integer id) {
        return DAirlineRepository.findById(id).orElse(null);
    }

}
