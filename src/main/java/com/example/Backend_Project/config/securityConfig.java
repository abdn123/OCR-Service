package com.example.backend_project.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;
    @Autowired
    AuthenticationProvider authenticationProvider;
    
    @Bean    
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors((cors -> cors.configurationSource(corsConfigurationSource())))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/me").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(
                (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage())
            ))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTION"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
