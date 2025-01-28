package com.example.login.config;

import com.example.login.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//public class SecurityConfig {
public class SecurityConfig {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean




    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/signup", "/auth/login", "/properties/add").permitAll()  // Allow token validation
                        .requestMatchers(HttpMethod.GET, "/properties/**").permitAll() // Allow GET requests to /properties/**
                        .requestMatchers(HttpMethod.DELETE, "/properties/**").permitAll() // Allow DELETE requests to /properties/**

                        .anyRequest().authenticated()
                );

        return http.build();
    }




//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/properties/add", "/properties/**") // Allow POST, PUT, DELETE for these endpoints
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
//                        .requestMatchers("/properties/add").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/properties/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }


}
