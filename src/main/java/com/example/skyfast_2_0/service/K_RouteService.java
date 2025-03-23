package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Route;
import com.example.skyfast_2_0.repository.K_RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class K_RouteService {
    private final K_RouteRepository KRouteRepository;

    public K_RouteService(K_RouteRepository KRouteRepository) {
        this.KRouteRepository = KRouteRepository;
    }

    public List<Object[]> findAllRoutesWithAirportNames() {
        return KRouteRepository.findAllRoutesWithAirportNames();
    }

    public void saveRoute(Route route) {
        KRouteRepository.save(route);
    }

    // Kiểm tra xem tuyến đường có tồn tại không
    public boolean isRouteExists(int departureAirportId, int arrivalAirportId) {
        return KRouteRepository.existsByDepartureAirportIdAndArrivalAirportId(departureAirportId, arrivalAirportId);
    }
}
