 package com.example.skyfast_2_0.controller;

 import com.example.skyfast_2_0.entity.Airline;
 import com.example.skyfast_2_0.service.AirlineService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequestMapping("/api/airlines")
 public class AirlineController {

     @Autowired
     private AirlineService airlineService;

     // GET - Read airline(s)
     @GetMapping
     public ResponseEntity<?> getAirlines(@RequestParam(required = false) Integer id) {
         if (id != null) {
             Airline airline = airlineService.getAirlineById(id);
             return ResponseEntity.ok(airline);
         }
         List<Airline> airlines = airlineService.getAllAirlines();
         return ResponseEntity.ok(airlines);
     }

     // POST - Create new airline
     @PostMapping
     public ResponseEntity<Airline> createAirline(@RequestBody Airline airline) {
         Airline createdAirline = airlineService.createAirline(airline);
         return ResponseEntity.ok(createdAirline);
     }

     // PUT - Update airline
     @PutMapping
     public ResponseEntity<Airline> updateAirline(@RequestParam Integer id, @RequestBody Airline airline) {
         airline.setId(id); // Đảm bảo ID được set đúng
         Airline updatedAirline = airlineService.updateAirline(airline);
         return ResponseEntity.ok(updatedAirline);
     }

     // DELETE - Delete airline
     @DeleteMapping
     public ResponseEntity<String> deleteAirline(@RequestParam Integer id) {
         airlineService.deleteAirline(id);
         return ResponseEntity.ok("Airline deleted successfully");
     }
 }