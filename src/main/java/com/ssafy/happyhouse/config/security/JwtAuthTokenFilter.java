package com.ssafy.happyhouse.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthTokenFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            jwtTokenProvider.verify(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException();
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) throws AuthenticationException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else {
            throw new AuthenticationException("인증 정보가 올바르지 않습니다.");
        }
    }

}
