package com.spring.practice.springSecurity.service;


import com.spring.practice.springSecurity.DTO.LoginDto;
import com.spring.practice.springSecurity.DTO.LoginResponseDto;
import com.spring.practice.springSecurity.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String accessToken =  jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return new LoginResponseDto(userEntity.getId(), accessToken, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdFromToken(refreshToken);

         UserEntity userEntity = userService.getUserByUserId(userId);

         String accessToken = jwtService.generateAccessToken(userEntity);

         return new LoginResponseDto(userEntity.getId(), accessToken, refreshToken);
    }
}
