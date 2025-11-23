package com.spring.practice.springSecurity.controller;

import com.spring.practice.springSecurity.DTO.BookDto;
import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.enums.Role;
import com.spring.practice.springSecurity.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    private  final ModelMapper mapper;

    public BookController(BookService bookService, ModelMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN","ROLE_CREATOR"})
    List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN','CREATOR') AND hasAuthority('BOOK_CREATE')")
    void addNewBook(@RequestBody BookDto bookDto){
       UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       bookDto.setUser(user);
       bookService.addBook(bookDto);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    BookDto getBookByID(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@bookSecurity.isOwnerOfBook(#id)")
    BookDto deleteById(@PathVariable long id){
        return bookService.deleteById(id);
    }
}
