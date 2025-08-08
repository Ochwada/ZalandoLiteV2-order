package com.owr.order_service.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*=================================================================================
 * Project: order-service
 * File: SecurityConfig
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 2:46 PM
 * Description:  Configures Spring Security to protect all endpoints using OAuth2 with JWT tokens.
 =================================================================================*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Defines the security filter chain for HTTP requests.
     *
     * @param httpSecurity HttpSecurity instance used to configure security rules.
     * @return A built SecurityFilterChain bean.
     * @throws Exception in case of configuration errors.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        // Require authentication for all other endpoints
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(jwtConfigurer -> {
                                    // Use default JWT decoder based on spring.security.oauth2.resourceserver.jwt.issuer-uri
                                    // You can add a custom converter/decoder here if needed
                                })
                );
        // Finalise and return the filter chain
        return httpSecurity.build(); // returns SecurityFilterChain object and registers it with Spring.
    }
}
