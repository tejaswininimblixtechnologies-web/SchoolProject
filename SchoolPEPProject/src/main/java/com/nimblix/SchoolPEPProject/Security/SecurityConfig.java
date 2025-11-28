package com.nimblix.SchoolPEPProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/student/**", "/login").permitAll() // allow these
                        .anyRequest().authenticated() // protect all other APIs
                )
                .httpBasic(httpBasic -> {}) // Basic Authentication
                .formLogin(form -> form.disable()); // Disable form login for REST API

        return http.build();
    }

}
