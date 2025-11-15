package com.spring.practice.springSecurity.controller;

import com.spring.practice.springSecurity.DTO.BookDto;
import com.spring.practice.springSecurity.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping
    void addNewBook(@RequestBody BookDto bookDto){
        bookService.addBook(bookDto);
    }

    @GetMapping("/{id}")
    BookDto getBookByID(@PathVariable Long id){
        return bookService.getBookById(id);

    }
}
