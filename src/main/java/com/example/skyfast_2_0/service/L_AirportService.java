 package com.example.skyfast_2_0.service;

 import com.example.skyfast_2_0.dto.L_AirportDTO;
 import com.example.skyfast_2_0.entity.Airport;
 import com.example.skyfast_2_0.mapper.L_AirportMapper;
 import com.example.skyfast_2_0.repository.L_AirportRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.stream.Collectors;

 @Service
 public class L_AirportService {

     @Autowired
     private L_AirportRepository airportRepository;

     @Autowired
     private L_AirportMapper airportMapper;

     public List<L_AirportDTO> getAllAirports() {
         return airportRepository.findByAirportStatus("ACTIVE")
                 .stream()
                 .map(airportMapper::toDTO)
                 .collect(Collectors.toList());
     }

     public L_AirportDTO getAirportById(Integer id) {
         Airport airport = airportRepository.findByIdAndAirportStatus(id, "ACTIVE")
                 .orElseThrow(() -> new RuntimeException("Airport not found"));
         return airportMapper.toDTO(airport);
     }

     public L_AirportDTO createAirport(L_AirportDTO airportDTO) {
         Airport airport = airportMapper.toEntity(airportDTO);
//         airport.setAirportStatus("ACTIVE");
         Airport savedAirport = airportRepository.save(airport);
         return airportMapper.toDTO(savedAirport);
     }

     public L_AirportDTO updateAirport(Integer id, L_AirportDTO airportDTO) {
         Airport existingAirport = airportRepository.findByIdAndAirportStatus(id, "ACTIVE")
                 .orElseThrow(() -> new RuntimeException("Airport not found"));

         Airport updatedAirport = airportMapper.toEntity(airportDTO);
         updatedAirport.setId(id);
         updatedAirport = airportRepository.save(updatedAirport);
         return airportMapper.toDTO(updatedAirport);
     }

     public void deleteAirport(Integer id) {
         Airport airport = airportRepository.findByIdAndAirportStatus(id, "ACTIVE")
                 .orElseThrow(() -> new RuntimeException("Airport not found"));
//         airport.setAirportStatus("INACTIVE");
         airportRepository.save(airport);
     }
 }