package com.spring.practice.springSecurity.advice;


import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalExceptionhandler {

   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> resourceNotFoundExpHandler(ResourceNotFoundException resourceNotFoundException){
       APIError apiError = new APIError(resourceNotFoundException.getLocalizedMessage(), HttpStatus.NOT_FOUND);
       return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIError> handleAuthenticationException( AuthenticationException exception){
       APIError apiError = new APIError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
       return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler(JwtException.class)
    public  ResponseEntity<APIError> handleJWTExp(JwtException exception){
       APIError apiError = new APIError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
       return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
   }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<APIError> accessDeniedExp(AccessDeniedException exception){
        APIError apiError = new APIError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}
