package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.repository.D_PlaneCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class D_PlaneCategoryService {

    @Autowired
    private D_PlaneCategoryRepository DPlaneCategoryRepository;

    public List<Airplane> getAllPlaneCategories() {
        // Trả về tất cả các PlaneCategory từ repository
        return DPlaneCategoryRepository.findAll();
    }
    public void saveAirplane(Airplane airplane) {
        DPlaneCategoryRepository.save(airplane);
    }

    public List<Airplane> searchAirplanes(String name, String status, String airlineName) {
        return DPlaneCategoryRepository.searchAirplanes(name, status, airlineName);
    }

    public Airplane findById(Integer id) {
        return DPlaneCategoryRepository.findById(id).orElse(null);
    }

    public void updateAirplane(Airplane airplane) {
        DPlaneCategoryRepository.save(airplane);
    }

    public boolean existsByAirplaneName(String airplaneName) {
        return DPlaneCategoryRepository.existsByAirplaneName(airplaneName);
    }


}
