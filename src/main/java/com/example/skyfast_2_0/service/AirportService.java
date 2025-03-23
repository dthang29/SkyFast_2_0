package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.AirportDTO;
import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.repository.AirportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private  final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(airport -> new AirportDTO(
                        airport.getId(),
                        airport.getAirportName(),
                        airport.getAirportCode(),
                        airport.getCountry(),
                        airport.getLocation()))
                .collect(Collectors.toList());
    }

    public AirportDTO getAirportById(Integer id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found"));
        return new AirportDTO(airport.getId(), airport.getAirportName(), airport.getAirportCode(), airport.getCountry(), airport.getLocation());
    }


    public AirportDTO createAirport(AirportDTO airportDTO) {
        if(airportDTO == null || airportDTO.getAirport_code() == null || airportDTO.getAirport_name() == null || airportDTO.getLocation() == null) {
            throw new IllegalArgumentException("Airport cannot be null");
        }
        boolean isAirportNameExists = airportRepository.existsByAirportName(airportDTO.getAirport_name());
        if(isAirportNameExists) {
            throw new IllegalArgumentException("Airport name already exists");
        }
        boolean isCodeExists = airportRepository.existsByAirportCode(airportDTO.getAirport_code());
        if(isCodeExists) {
            throw new IllegalArgumentException("Airport code already exists");
        }
        Airport airport = new Airport();
        airport.setAirportName(airportDTO.getAirport_name());
        airport.setAirportCode(airportDTO.getAirport_code());
        airport.setCountry(airportDTO.getCountry());
        airport.setLocation(airportDTO.getLocation());
        airportRepository.save(airport);
        return new AirportDTO(airport.getId(), airport.getAirportName(), airport.getAirportCode(), airport.getCountry(), airport.getLocation());
    }

    public Airport findByName(String name) {
        return airportRepository.findByAirportName(name);
    }
}
