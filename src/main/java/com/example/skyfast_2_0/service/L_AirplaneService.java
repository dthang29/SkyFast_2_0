 package com.example.skyfast_2_0.service;

 import com.example.skyfast_2_0.dto.L_AirplaneDTO;
 import com.example.skyfast_2_0.entity.Airline;
 import com.example.skyfast_2_0.entity.Airplane;
 import com.example.skyfast_2_0.mapper.L_AirplaneMapper;
 import com.example.skyfast_2_0.repository.L_AirlineRepository;
 import com.example.skyfast_2_0.repository.L_AirplaneRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.stream.Collectors;

 @Service
 public class L_AirplaneService {

     @Autowired
     private L_AirplaneRepository airplaneRepository;

     @Autowired
     private L_AirlineRepository airlineRepository;

     @Autowired
     private L_AirplaneMapper airplaneMapper;

     public List<L_AirplaneDTO> getAllAirplanes() {
         return airplaneRepository.findByAirplaneStatus("Waiting")
                 .stream()
                 .map(airplaneMapper::toDTO)
                 .collect(Collectors.toList());
     }


     public L_AirplaneDTO getAirplaneById(Integer id) {
         Airplane airplane = airplaneRepository.findByIdAndAirplaneStatus(id, "Waiting")
                 .orElseThrow(() -> new RuntimeException("Airplane not found"));
         return airplaneMapper.toDTO(airplane);
     }

     public L_AirplaneDTO createAirplane(L_AirplaneDTO airplaneDTO) {
         Airplane airplane = airplaneMapper.toEntity(airplaneDTO);

         // Fetch and set Airline
         Airline airline = airlineRepository.findById(airplaneDTO.getAirlineId())
                 .orElseThrow(() -> new RuntimeException("Airline not found"));
         airplane.setAirline(airline);

         airplane.setAirplaneStatus("Waiting");
         Airplane savedAirplane = airplaneRepository.save(airplane);
         return airplaneMapper.toDTO(savedAirplane);
     }

     public L_AirplaneDTO updateAirplane(Integer id, L_AirplaneDTO airplaneDTO) {
         Airplane existingAirplane = airplaneRepository.findByIdAndAirplaneStatus(id, "Waiting")
                 .orElseThrow(() -> new RuntimeException("Airplane not found"));

         Airplane updatedAirplane = airplaneMapper.toEntity(airplaneDTO);
         updatedAirplane.setId(id);

         // Fetch and set Airline
         Airline airline = airlineRepository.findById(airplaneDTO.getAirlineId())
                 .orElseThrow(() -> new RuntimeException("Airline not found"));
         updatedAirplane.setAirline(airline);

         updatedAirplane = airplaneRepository.save(updatedAirplane);
         return airplaneMapper.toDTO(updatedAirplane);
     }

     public void deleteAirplane(Integer id) {
         Airplane airplane = airplaneRepository.findByIdAndAirplaneStatus(id, "Waiting")
                 .orElseThrow(() -> new RuntimeException("Airplane not found"));
         airplane.setAirplaneStatus("INACTIVE");
         airplaneRepository.save(airplane);
     }
 }