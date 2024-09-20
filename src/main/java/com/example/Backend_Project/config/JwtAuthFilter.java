package com.example.Backend_Project.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Backend_Project.Services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
        ) throws ServletException, IOException {
        
            
            final String AuthHeader = request.getHeader("Authorization");
            
            if(AuthHeader == null || !AuthHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                final String jwtToken = AuthHeader.substring(7);
                final String username = jwtService.extractUsername(jwtToken);
                
                System.out.println("JWT Auth Filter triggered.");
                System.out.println("Auth Header: " + AuthHeader);
                System.out.println("Username extracted: " + username);


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                
                if(username != null && authentication == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    
                    if(jwtService.isTokenValid(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw e;
        }
    }
}

