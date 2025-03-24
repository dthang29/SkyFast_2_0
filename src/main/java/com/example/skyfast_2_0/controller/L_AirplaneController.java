 package com.example.skyfast_2_0.controller;

 import com.example.skyfast_2_0.dto.L_AirplaneDTO;
 import com.example.skyfast_2_0.service.L_AirplaneService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequestMapping("/api/airplanes")
 public class L_AirplaneController {

     @Autowired
     private L_AirplaneService airplaneService;

     @GetMapping
     public ResponseEntity<List<L_AirplaneDTO>> getAllAirplanes() {
         return ResponseEntity.ok(airplaneService.getAllAirplanes());
     }

     @GetMapping("/{id}")
     public ResponseEntity<L_AirplaneDTO> getAirplaneById(@PathVariable Integer id) {
         return ResponseEntity.ok(airplaneService.getAirplaneById(id));
     }

     @PostMapping
     public ResponseEntity<L_AirplaneDTO> createAirplane(@RequestBody L_AirplaneDTO airplaneDTO) {
         return ResponseEntity.ok(airplaneService.createAirplane(airplaneDTO));
     }

     @PutMapping("/{id}")
     public ResponseEntity<L_AirplaneDTO> updateAirplane(@PathVariable Integer id,
                                                         @RequestBody L_AirplaneDTO airplaneDTO) {
         return ResponseEntity.ok(airplaneService.updateAirplane(id, airplaneDTO));
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteAirplane(@PathVariable Integer id) {
         airplaneService.deleteAirplane(id);
         return ResponseEntity.ok().build();
     }
 }