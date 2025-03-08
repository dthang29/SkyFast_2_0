package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.repository.ClassCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassCategoryService {
    @Autowired
    private ClassCategoryRepository classCategoryRepository;

    public List<Classcategory> getAllClasCategories() {
        return classCategoryRepository.findAllClassCategories();
    }
}