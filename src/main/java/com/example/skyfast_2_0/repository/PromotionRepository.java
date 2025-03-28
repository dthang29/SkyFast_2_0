package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Promotion findPromotionByCode(String promotionCode);
}
