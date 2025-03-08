package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.constant.Status;
import com.example.skyfast_2_0.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    // Read operations
    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    public Airline getAirlineById(Integer id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with id: " + id));
    }

    // Create operation
    public Airline createAirline(Airline airline) {
        if (airline.getAirlineName() == null || airline.getAirlineName().trim().isEmpty()) {
            throw new RuntimeException("Airline name cannot be empty");
        }
        airline.setStatus(Status.ACTIVE);
        return airlineRepository.save(airline);
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

        return airlineRepository.save(existingAirline);
    }

    // Soft delete operation
    public void deleteAirline(Integer id) {
        Airline airline = getAirlineById(id);
        airline.setStatus(Status.INACTIVE);
        airlineRepository.save(airline);
    }
}