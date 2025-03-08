package com.example.skyfast_2_0.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class VerifyCodeService {
    private static final SecureRandom random = new SecureRandom();

    public String generateCode() {
        int otp = random.nextInt(1000000);
        return String.format("%06d", otp);
    }
}
