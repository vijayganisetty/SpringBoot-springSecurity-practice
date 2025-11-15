package com.spring.practice.springSecurity.service;

import com.spring.practice.springSecurity.DTO.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    void addBook(BookDto bookDto);
}
