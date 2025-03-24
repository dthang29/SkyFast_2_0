 package com.example.skyfast_2_0.controller;

 import com.example.skyfast_2_0.dto.L_RouteDTO;
 import com.example.skyfast_2_0.service.L_RouteService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequestMapping("/api/routes")
 public class L_RouteController {

     @Autowired
     private L_RouteService routeService;

     @GetMapping
     public ResponseEntity<List<L_RouteDTO>> getAllRoutes() {
         return ResponseEntity.ok(routeService.getAllRoutes());
     }

     @GetMapping("/{id}")
     public ResponseEntity<L_RouteDTO> getRouteById(@PathVariable Integer id) {
         return ResponseEntity.ok(routeService.getRouteById(id));
     }

     @PostMapping
     public ResponseEntity<L_RouteDTO> createRoute(@RequestBody L_RouteDTO routeDTO) {
         return ResponseEntity.ok(routeService.createRoute(routeDTO));
     }

     @PutMapping("/{id}")
     public ResponseEntity<L_RouteDTO> updateRoute(@PathVariable Integer id,
                                                   @RequestBody L_RouteDTO routeDTO) {
         return ResponseEntity.ok(routeService.updateRoute(id, routeDTO));
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteRoute(@PathVariable Integer id) {
         routeService.deleteRoute(id);
         return ResponseEntity.ok().build();
     }
 }