package com.spring.practice.springSecurity.advice;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
