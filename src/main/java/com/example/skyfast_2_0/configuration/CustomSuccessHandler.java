package com.example.skyfast_2_0.configuration;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String redirectUrl = "/homepage";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_CUSTOMER")) {
                redirectUrl = "/homepage";
                break;
            } else if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/bookings";
                break;
            } else if (role.equals("ROLE_STAFF")) {
                redirectUrl = "/staff/refund";
                break;
            } else if(role.equals("ROLE_MANAGER")) {
                redirectUrl = "/manager/airlinelist";
                break;
            }
        }
        if (authorities.isEmpty()) {
            throw new IllegalStateException();
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}