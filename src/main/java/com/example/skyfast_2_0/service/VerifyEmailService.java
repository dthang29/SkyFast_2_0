package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.repository.T_UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyEmailService {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    private final Map<String, String> codeStorage = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void sendUserEmail(String email)
            throws MessagingException {
        codeStorage.remove(email);
        String code = verifyCodeService.generateCode();
        codeStorage.put(email, code);
        emailSenderService.sendEmail(email, code);
        scheduler.schedule(() -> codeStorage.remove(email), 5, TimeUnit.MINUTES);
    }

    public boolean checkCode(String email, String code) {
        if (code.equals(codeStorage.get(email))) {
            return true;
        }
        return false;
    }

}
