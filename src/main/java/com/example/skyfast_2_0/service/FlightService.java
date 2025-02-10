package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getFlightsbyDepaTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return flightRepository.findFlightsByDepartureTimeRange(startOfDay, endOfDay);
    }


}