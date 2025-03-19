package com.example.skyfast_2_0.configuration;

import com.example.skyfast_2_0.constant.Role;
import com.example.skyfast_2_0.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] STATIC_RESOURCE = {"/css/**", "/font/**", "/js/**", "/image/**"};
    private final LoginService customUserDetailsService;

    public SecurityConfig(LoginService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(STATIC_RESOURCE).permitAll()
                                .requestMatchers("/homepage").permitAll()
//                                .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
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
                                        .userService(oauth2UserService()))
//                                .successHandler(successHandler)
                                .defaultSuccessUrl("/homepage", true)
                                .failureUrl("/login?error=true")
                                .loginPage("/login")
                                .permitAll()
                ).
                formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
//                                .successHandler(successHandler)
                                .defaultSuccessUrl("/homepage", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                ).rememberMe(rememberMe -> rememberMe
                        .key("rememberMe")
                        .tokenValiditySeconds(259200)
                        .userDetailsService(customUserDetailsService))
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
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
