package com.spring.practice.springSecurity.DTO;

import com.spring.practice.springSecurity.entity.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookDto {
    private String name;
    private String author;

    private UserEntity user;
}
