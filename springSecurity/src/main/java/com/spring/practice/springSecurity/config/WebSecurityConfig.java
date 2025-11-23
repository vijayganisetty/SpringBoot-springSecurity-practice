package com.spring.practice.springSecurity.config;


import com.spring.practice.springSecurity.filters.JwtAuthFilter;
import com.spring.practice.springSecurity.handlers.Oauth2Successhandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.spring.practice.springSecurity.enums.Role.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final Oauth2Successhandler oauth2Successhandler;
    private final JwtAuthFilter jwtAuthFilter;

    public WebSecurityConfig(Oauth2Successhandler oauth2Successhandler, JwtAuthFilter jwtAuthFilter) {
        this.oauth2Successhandler = oauth2Successhandler;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**","/home.html*").permitAll()
                        .requestMatchers( "/books/add").hasAnyRole(CREATOR.name(),ADMIN.name())
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config ->
                        oauth2Config
                                .failureUrl("/login?error=false")
                                .successHandler(oauth2Successhandler));
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
    }
}



