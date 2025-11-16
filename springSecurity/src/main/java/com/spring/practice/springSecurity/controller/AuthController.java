package com.spring.practice.springSecurity.controller;

import com.spring.practice.springSecurity.DTO.LoginDto;
import com.spring.practice.springSecurity.DTO.SignUpDTO;
import com.spring.practice.springSecurity.DTO.UserDTO;
import com.spring.practice.springSecurity.service.AuthService;
import com.spring.practice.springSecurity.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        String token = authService.login(loginDto);
        Cookie cookie = new Cookie("Token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
