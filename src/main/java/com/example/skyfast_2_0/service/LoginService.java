package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.constant.Role;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private T_UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = findByUserNameOrEmail(usernameOrEmail);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    public void processOAuthPostLogin(OAuth2User oAuth2User){
        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User user = userRepository.findByEmail(email);

        if(user == null){
            user = new User();
            user.setUserName(name);
            user.setEmail(email);
            user.setGoogleId(googleId);
            user.setPassword("OAUTH_DEFAULT_PASSWORD");
            user.setRole(Role.CUSTOMER);
            user.setCreatedAt(LocalDate.now());
            user.setStatus("Active");
            userRepository.save(user);
        }
        else if (user.getGoogleId() == null){
            user.setGoogleId(googleId);
            userRepository.save(user);
        }

        UserDetails userDetails = createOAuthUserDetails(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities()));
    }

    private UserDetails createOAuthUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password("")
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    private User findByUserNameOrEmail(String usernameOrEmail) {
        User user = userRepository.findByUserName(usernameOrEmail);
        if(user == null){
            user = userRepository.findByEmail(usernameOrEmail);
        }
        return user;
    }

    public boolean getUserByEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }

    public String getUserPasswordByEmail(String email) {
        return userRepository.findByEmail(email).getPassword();
    }

    public boolean getUserByUsername(String userName) {
        if (userRepository.findByUserName(userName) != null) {
            return true;
        }
        return false;
    }

    public String getUserPasswordByUsername(String userName) {
        return userRepository.findByUserName(userName).getPassword();
    }

}
