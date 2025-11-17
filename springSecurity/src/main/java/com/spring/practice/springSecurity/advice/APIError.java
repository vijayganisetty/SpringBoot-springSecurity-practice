package com.spring.practice.springSecurity.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIError{

    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus status;

    public APIError(String message, HttpStatus status){
        this();
        this.message = message;
        this.status = status;

    }

    public APIError() {
        this.localDateTime = LocalDateTime.now();
    }
}
