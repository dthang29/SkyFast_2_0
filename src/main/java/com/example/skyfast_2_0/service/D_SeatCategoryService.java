package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.repository.D_ClasscategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class D_SeatCategoryService {

    @Autowired
    private D_ClasscategoryRepository DClasscategoryRepository;

    public List<Classcategory> findByAirplane(Airplane airplane) {
        return DClasscategoryRepository.findByAirplane(airplane);
    }

    public boolean existsByAirplaneAndName(Airplane airplane, String name) {
        return DClasscategoryRepository.existsByAirplaneAndName(airplane, name);
    }

    public Classcategory findById(Integer id) {
        return DClasscategoryRepository.findById(id).orElse(null);
    }

    public void save(Classcategory classcategory) {
        DClasscategoryRepository.save(classcategory);
    }
}


