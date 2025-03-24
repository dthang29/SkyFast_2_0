package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Transactional
    public void insertPassenger(String title, String firstName, String lastName, String nation, String phone, String email) {
        String name = lastName + " " + firstName;
        passengerRepository.insert(title, name, nation, phone, email);
    }
}
