package com.readingisgood.orderservice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractTokenFromHeader(httpServletRequest);
            if (StringUtils.hasText(token) && jwtUtil.validateJwtToken(token)) {
                authenticateUser(httpServletRequest, token);
            }
        }catch (Exception e){
            log.error("Cannot set user authentication: {0}", e);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void authenticateUser(HttpServletRequest httpServletRequest, String token) {
        String subject = jwtUtil.parseJWT(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String token = null;
        String authorization = request.getHeader("Authorization");
        String TOKEN_PREFIX = "Bearer ";
        if(authorization != null  && authorization.length() >= TOKEN_PREFIX.length())
            token = authorization.substring(TOKEN_PREFIX.length());
        return token;
    }
}
