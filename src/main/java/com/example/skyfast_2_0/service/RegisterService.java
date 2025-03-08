package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private T_UserRepository userRepository;

    public boolean checkPasswordSameWithConfirmPassword(String password,
                                                        String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkEmailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean isOnlySpaces(String inputText) {
        if(inputText != null && inputText.trim().isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkUsernameExist(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean checkPhoneExist(String phone) {
        User user = userRepository.findByPhoneNumber(phone);
        if (user != null) {
            return true;
        }
        return false;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
