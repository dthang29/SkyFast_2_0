package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Paymenttype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface T_PaymentTypeRepository extends JpaRepository<Paymenttype, Integer> {
    Paymenttype findById(int id);
}
