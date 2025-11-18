package com.spring.practice.springSecurity.handlers;

import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.service.JwtService;
import com.spring.practice.springSecurity.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2Successhandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    public Oauth2Successhandler(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User oauthuser = (DefaultOAuth2User) token.getPrincipal();
        logger.info(oauthuser.getAttributes());

        String email = oauthuser.getAttribute("email");

        UserEntity userEntity = userService.getUserByMail(email);

        if(userEntity == null){
            UserEntity newUser = new UserEntity();
            newUser.setName(oauthuser.getAttribute("name"));
            newUser.setEmail(email);
           userEntity = userService.save(newUser);
        }
         String accessToken = jwtService.generateAccessToken(userEntity);
         String refreshToken = jwtService.generateRefreshToken(userEntity);

        Cookie cookie = new Cookie("RefreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8080/home.html?token=?"+accessToken);
    }

}
