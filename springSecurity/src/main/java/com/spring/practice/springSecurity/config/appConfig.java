package com.spring.practice.springSecurity.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
