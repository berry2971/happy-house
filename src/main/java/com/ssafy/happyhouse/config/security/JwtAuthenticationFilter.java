package com.ssafy.happyhouse.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            String id = jwtTokenProvider.getIdFromToken(token);
            UserAuthentication userAuthentication = new UserAuthentication(id, null, null);
            userAuthentication.s
        } catch (AuthenticationException e) {

        }

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
