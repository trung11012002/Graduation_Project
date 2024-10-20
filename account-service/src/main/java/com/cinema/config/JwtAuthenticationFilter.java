package com.cinema.config;

import com.cinema.exception.AppException;
import com.cinema.exception.ErrorCode;
import com.cinema.dto.response.CustomUserDetails;
import com.cinema.service.impl.CustomUserDetailsService;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String[] PUBLIC_ENDPOINTS = {
            "/auth/**",
            "/user/**",
    };

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        for (String publicEndpoint : PUBLIC_ENDPOINTS) {
            if (requestURI.startsWith(publicEndpoint.replace("**", ""))) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = null;
        String username = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // Check valid token
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                username = signedJWT.getJWTClaimsSet().getSubject();
            } catch (ParseException | AppException e) {
                jwtAuthenticationEntryPoint.commence(
                        request, response, new AuthenticationException("User is not authenticated") {});
                return;
            }

            if (username != null) {
                CustomUserDetails userDetails;
                try {
                    userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e) {
                    throw new AppException(ErrorCode.UNAUTHENTICATED);
                }
                // check status active
//                if (!userDetails.getActive()) {
//                    jwtAuthenticationEntryPoint.commence(
//                            request, response, new AuthenticationException("User is not active") {});
//                    return;
//                }

                SecurityContextHolder.clearContext();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
