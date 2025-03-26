package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Baggage;
import com.example.skyfast_2_0.repository.BaggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaggageService {
    @Autowired
    private BaggageRepository baggageRepository;
    public List<Baggage> findByAirlineId(Integer airlineId) {
        return baggageRepository.findByAirlineId(airlineId);
    }
    public String totalPriceBagaage(List<Integer> baggageId) {
        Float totalPriceBagaage = 0f;
        for (Integer i : baggageId) {
            if(i != 0) {
                totalPriceBagaage += baggageRepository.getBaggageById(i).getBaggagePrice();
            }
        }
        return totalPriceBagaage + "";
    }
}
