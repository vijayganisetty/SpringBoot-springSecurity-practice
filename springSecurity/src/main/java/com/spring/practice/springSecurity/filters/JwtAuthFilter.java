package com.spring.practice.springSecurity.filters;

import com.spring.practice.springSecurity.DTO.UserDTO;
import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.service.JwtService;
import com.spring.practice.springSecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;


    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

        @Override
        protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain
        filterChain) throws ServletException, IOException {

        try {

        final String requestToken = request.getHeader("Authorization");

        if (requestToken == null || !requestToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestToken.split("Bearer ")[1];

        Long userId = jwtService.getUserIdFromToken(token);
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = userService.getUserByUserId(userId);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userEntity, null, null);

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
        }
        catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
