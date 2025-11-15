package com.spring.practice.springSecurity.service;

import com.spring.practice.springSecurity.DTO.BookDto;
import com.spring.practice.springSecurity.entity.BookEntity;
import com.spring.practice.springSecurity.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }


    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        List<BookDto> bookDtos = bookEntities.stream().map(bookEntity -> mapper.map(bookEntity, BookDto.class)).toList();
        return bookDtos;
    }

    public BookDto getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow();
        return mapper.map(bookEntity, BookDto.class);
    }

    public void addBook(BookDto bookDto) {
         BookEntity bookEntity = mapper.map(bookDto, BookEntity.class);
         bookRepository.save(bookEntity);
    }
}
