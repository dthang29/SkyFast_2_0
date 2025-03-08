package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.constant.Status;
import com.example.skyfast_2_0.repository.L_AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class L_AirlineService {

    @Autowired
    private L_AirlineRepository LAirlineRepository;

    // Read operations
    public List<Airline> getAllAirlines() {
        return LAirlineRepository.findAll();
    }

    public Airline getAirlineById(Integer id) {
        return LAirlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with id: " + id));
    }

    // Create operation
    public Airline createAirline(Airline airline) {
        if (airline.getAirlineName() == null || airline.getAirlineName().trim().isEmpty()) {
            throw new RuntimeException("Airline name cannot be empty");
        }
        airline.setStatus(Status.ACTIVE);
        return LAirlineRepository.save(airline);
    }

    // Update operation
    public Airline updateAirline(Airline airline) {
        Airline existingAirline = getAirlineById(airline.getId());

        existingAirline.setAirlineName(airline.getAirlineName());
        existingAirline.setCountryOfOperation(airline.getCountryOfOperation());
        existingAirline.setFoundedDate(airline.getFoundedDate()); // LocalDate
        existingAirline.setImage(airline.getImage());

        if (airline.getStatus() != null) {
            existingAirline.setStatus(airline.getStatus());
        }

        return LAirlineRepository.save(existingAirline);
    }

    // Soft delete operation
    public void deleteAirline(Integer id) {
        Airline airline = getAirlineById(id);
        airline.setStatus(Status.INACTIVE);
        LAirlineRepository.save(airline);
    }
}