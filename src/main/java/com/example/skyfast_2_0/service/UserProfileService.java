package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class UserProfileService {

    @Autowired
    private T_UserRepository userRepository;

    public void updateUserProfile(String email, User updatedUser) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setUpdateAt(LocalDate.now());
            userRepository.save(existingUser);
        }
    }

    public String getUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) authentication.getPrincipal()).getAttributes();
            return (String) attributes.get("email");
        }
        return null;
    }

    public boolean checkPhoneNumberExist(String phoneNum) {
        return userRepository.existsByPhoneNumber(phoneNum);
    }

}
