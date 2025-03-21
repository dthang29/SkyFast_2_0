package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.entity.Promotion;
import com.example.skyfast_2_0.dto.K_PromotionDTO;
import com.example.skyfast_2_0.repository.K_AirlineRepository;
import com.example.skyfast_2_0.repository.K_PromotionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class K_PromotionService {
    @Autowired
    private K_AirlineRepository KAirlineRepository;

    private final K_PromotionRepository KPromotionRepository;

    public K_PromotionService(K_PromotionRepository KPromotionRepository) {this.KPromotionRepository = KPromotionRepository;}

    public List<K_PromotionDTO> getAllPromotions() {
        return KPromotionRepository.findAll().stream()
                .map(promotion -> new K_PromotionDTO(
                        promotion.getId(),
                        promotion.getCode(),
                        promotion.getDescription(),
                        promotion.getDiscountPercentage(),
                        promotion.getStartDate(),
                        promotion.getEndDate(),
                        promotion.getStatus(),
                        promotion.getAirline() != null ? promotion.getAirline().getId() : null,
                        promotion.getAirline() != null ? promotion.getAirline().getAirlineName() : null))
                .collect(Collectors.toList());
    }

    public K_PromotionDTO getPromotionById(Integer id) {
        Promotion promotion = KPromotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));
        Integer airlineId = (promotion.getAirline() != null) ? promotion.getAirline().getId() : null;
        String airlineName = (promotion.getAirline() != null) ? promotion.getAirline().getAirlineName() : null;
        return new K_PromotionDTO(promotion.getId(), promotion.getCode(), promotion.getDescription(), promotion.getDiscountPercentage(),
                promotion.getStartDate(), promotion.getEndDate(), promotion.getStatus(), airlineId, airlineName);
    }

    public K_PromotionDTO updatePromotionStatus(Integer id, LocalDate endDate, String status) {
        Promotion promotion = KPromotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));


        // Kiểm tra nếu endDate nhỏ hơn startDate thì báo lỗi
        if (endDate.isBefore(promotion.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        promotion.setEndDate(endDate);
        promotion.setStatus(status);
        KPromotionRepository.save(promotion);
        return new K_PromotionDTO(promotion.getId(), promotion.getCode(), promotion.getDescription(), promotion.getDiscountPercentage(),
                promotion.getStartDate(), promotion.getEndDate(), promotion.getStatus(), promotion.getAirline().getId(), promotion.getAirline().getAirlineName());
    }

    public K_PromotionDTO createPromotion(K_PromotionDTO KPromotionDTO) {
        if(KPromotionDTO == null || KPromotionDTO.getAirlineName() == null || KPromotionDTO.getAirlineName().isEmpty()) {
            throw new IllegalArgumentException("Promotion Data or Airline Name must not be null or empty");
        }
        // Kiểm tra xem mã code đã tồn tại chưa
        boolean isCodeExists = KPromotionRepository.existsByCode(KPromotionDTO.getCode());
        if (isCodeExists) {
            throw new IllegalArgumentException("Promotion code already exists: " + KPromotionDTO.getCode());
        }
        // Kiểm tra ngày hợp lệ
        if (KPromotionDTO.getEndDate().isBefore(KPromotionDTO.getStartDate())) {
            throw new IllegalArgumentException("End Date cannot be before Start Date");
        }
//       // lấy airline từ database
        Airline airline = KAirlineRepository.findByAirlineName(KPromotionDTO.getAirlineName())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found " + KPromotionDTO.getAirlineName()));
        // Tạo mới Promotion
        Promotion promotion = new Promotion();
        promotion.setCode(KPromotionDTO.getCode());
        promotion.setDescription(KPromotionDTO.getDescription());
        promotion.setDiscountPercentage(KPromotionDTO.getDiscountPercentage());
        promotion.setStartDate(KPromotionDTO.getStartDate());
        promotion.setEndDate(KPromotionDTO.getEndDate());
        promotion.setStatus(KPromotionDTO.getStatus());
        promotion.setAirline(airline);
        KPromotionRepository.save(promotion);
        return new K_PromotionDTO(promotion.getId(), promotion.getCode(),
                promotion.getDescription(), promotion.getDiscountPercentage(),
                promotion.getStartDate(), promotion.getEndDate(),
                promotion.getStatus(), promotion.getAirline().getId(), promotion.getAirline().getAirlineName());
    }

}
