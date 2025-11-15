package com.spring.practice.springSecurity.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookDto {
    private String name;
    private String author;
}
