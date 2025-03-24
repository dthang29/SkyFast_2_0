// package com.example.skyfast_2_0.controller;
//
// import com.example.skyfast_2_0.dto.L_AirportDTO;
// import com.example.skyfast_2_0.service.L_AirportService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.List;
//
// @RestController
// @RequestMapping("/api/airports")
// public class L_AirportController {
//
//     @Autowired
//     private L_AirportService airportService;
//
//     @GetMapping
//     public ResponseEntity<List<L_AirportDTO>> getAllAirports() {
//         return ResponseEntity.ok(airportService.getAllAirports());
//     }
//
//     @GetMapping("/{id}")
//     public ResponseEntity<L_AirportDTO> getAirportById(@PathVariable Integer id) {
//         return ResponseEntity.ok(airportService.getAirportById(id));
//     }
//
//     @PostMapping
//     public ResponseEntity<L_AirportDTO> createAirport(@RequestBody L_AirportDTO airportDTO) {
//         return ResponseEntity.ok(airportService.createAirport(airportDTO));
//     }
//
//     @PutMapping("/{id}")
//     public ResponseEntity<L_AirportDTO> updateAirport(@PathVariable Integer id,
//                                                       @RequestBody L_AirportDTO airportDTO) {
//         return ResponseEntity.ok(airportService.updateAirport(id, airportDTO));
//     }
//
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteAirport(@PathVariable Integer id) {
//         airportService.deleteAirport(id);
//         return ResponseEntity.ok().build();
//     }
// }