package com.example.skyfast_2_0.configuration;

import com.example.skyfast_2_0.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(STATIC_RESOURCE).permitAll()
                                .requestMatchers("/home/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/verify-code",
                                        "/change-password",
                                        "/forgot-password", "/verify-email").permitAll()
                                .requestMatchers("/resend-code").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                        .userService(oauth2UserService()))
                                .defaultSuccessUrl("/home", true)
                                .loginPage("/auth/Login")
                                .permitAll()
                ).
                formLogin(
                        form -> form
                                .loginPage("/auth/Login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );
        return http.build();
    }


//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(){
//        return new DefaultOAuth2UserService() {
//            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//                OAuth2User oAuth2User = super.loadUser(userRequest);
//                customUserDetailsService.processOAuthPostLogin(oAuth2User);
//                return oAuth2User;
//            }
//        };
//    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
            customUserDetailsService.processOAuthPostLogin(oAuth2User);
            return oAuth2User;
        };
    }

}
