package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.repository.AirPlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirPlaneService {
    @Autowired
    private AirPlaneRepository airPlaneRepository;

    public List<Airplane> getAllAirplanes() {
        return airPlaneRepository.findAllPlane();
    }
    public Airplane getAirplaneById(int id) {
        return airPlaneRepository.findById(id);
    }
}