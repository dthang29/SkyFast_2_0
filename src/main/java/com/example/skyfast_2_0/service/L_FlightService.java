package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.L_FlightDTO;
import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.entity.Route;
import com.example.skyfast_2_0.mapper.L_FlightMapper;
import com.example.skyfast_2_0.repository.L_AirlineRepository;
import com.example.skyfast_2_0.repository.L_AirplaneRepository;
import com.example.skyfast_2_0.repository.L_FlightRepository;
import com.example.skyfast_2_0.repository.L_RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class L_FlightService {

    @Autowired
    private L_FlightRepository flightRepository;

    @Autowired
    private L_AirlineRepository airlineRepository;

    @Autowired
    private L_AirplaneRepository airplaneRepository;

    @Autowired
    private L_RouteRepository routeRepository;

    @Autowired
    private L_FlightMapper flightMapper;

    public List<L_FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        System.out.println("Flights retrieved from DB: " + flights);
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Flight> getAllFlights2(){
        return flightRepository.findAll();
    }

    public L_FlightDTO getFlightById(Integer id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return flightMapper.toDTO(flight);
    }

    public L_FlightDTO createFlight(L_FlightDTO flightDTO) {
        System.out.println("FlightDTO received: " + flightDTO);
        if (flightDTO.getPrice() == null) {
            throw new RuntimeException("Price cannot be null");
        }
        if (flightDTO.getDepartureTime() == null || flightDTO.getArrivalTime() == null) {
            throw new RuntimeException("Departure and Arrival times cannot be null");
        }
        if (flightDTO.getFlightNumber() == null || flightDTO.getFlightNumber().trim().isEmpty()) {
            throw new RuntimeException("Flight number cannot be null or empty");
        }

        Flight flight = flightMapper.toEntity(flightDTO);

        // Fetch and set Airline
        Airline airline = airlineRepository.findById(flightDTO.getAirlineId())
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + flightDTO.getAirlineId()));
        flight.setAirline(airline);

        // Fetch and set Airplane
        Airplane airplane = airplaneRepository.findById(flightDTO.getAirplaneId())
                .orElseThrow(() -> new RuntimeException("Airplane not found with ID: " + flightDTO.getAirplaneId()));
        flight.setAirplane(airplane);

        // Fetch and set Route
        Route route = routeRepository.findById(flightDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + flightDTO.getRouteId()));
        flight.setRoute(route);

        flight.setStatusFlight("ACTIVE");
        Flight savedFlight = flightRepository.save(flight);
        System.out.println("Flight saved to DB: " + savedFlight);
        return flightMapper.toDTO(savedFlight);
    }

    public L_FlightDTO updateFlight(Integer id, L_FlightDTO flightDTO) {
        System.out.println("Received FlightDTO for update: " + flightDTO);
        Flight existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Flight updatedFlight = flightMapper.toEntity(flightDTO);
        updatedFlight.setId(id);

        if (flightDTO.getDepartureTime() == null || flightDTO.getArrivalTime() == null) {
            throw new RuntimeException("Departure time and Arrival time must not be null");
        }

        // Fetch and set Airline
        Airline airline = airlineRepository.findById(flightDTO.getAirlineId())
                .orElseThrow(() -> new RuntimeException("Airline not found"));
        updatedFlight.setAirline(airline);

        // Fetch and set Airplane
        Airplane airplane = airplaneRepository.findById(flightDTO.getAirplaneId())
                .orElseThrow(() -> new RuntimeException("Airplane not found"));
        updatedFlight.setAirplane(airplane);

        // Fetch and set Route
        Route route = routeRepository.findById(flightDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        updatedFlight.setRoute(route);

        updatedFlight = flightRepository.save(updatedFlight);
        System.out.println("Updated flight saved: " + updatedFlight);
        return flightMapper.toDTO(updatedFlight);
    }

    public void deleteFlight(Integer id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        flight.setStatusFlight("INACTIVE");
        flightRepository.save(flight);
    }
}