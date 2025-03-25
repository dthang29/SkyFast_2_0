package com.example.skyfast_2_0.configuration;

import com.example.skyfast_2_0.service.CustomOAuth2UserService;
import com.example.skyfast_2_0.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] STATIC_RESOURCE = {"/css/**", "/js/**", "/img/**", "/assets/**"};
    private final LoginService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(LoginService customUserDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.customUserDetailsService = customUserDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, CustomSuccessHandler successHandler) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(STATIC_RESOURCE).permitAll()
                                .requestMatchers("/homepage").permitAll()
                                .requestMatchers("/api/airplanes/**",
                                        "/manager/**",
                                        "/api/routes/**").hasRole("MANAGER")
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/staff/**").hasAnyRole("STAFF", "MANAGER")
                                .requestMatchers("/homepage/booking-history",
                                        "/homepage/refund").hasRole("CUSTOMER")
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/verify-code",
                                        "/reset-password",
                                        "/forgot-password",
                                        "/verify-email",
                                        "/resend").permitAll()
                                .requestMatchers("/resend-code").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                        .userService(customOAuth2UserService))
                                .successHandler(successHandler)
                                .failureUrl("/login?error=true")
                                .loginPage("/login")
                                .permitAll()
                ).
                formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(successHandler)
                                .failureUrl("/login?error=true")
                                .permitAll()
                ).rememberMe(rememberMe -> rememberMe
                        .key("rememberMe")
                        .tokenValiditySeconds(259200)
                        .userDetailsService(customUserDetailsService))
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/homepage")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID", "rememberMe")
                                .permitAll()
                );
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
            customUserDetailsService.processOAuthPostLogin(oAuth2User);
            return oAuth2User;
        };
    }
}
