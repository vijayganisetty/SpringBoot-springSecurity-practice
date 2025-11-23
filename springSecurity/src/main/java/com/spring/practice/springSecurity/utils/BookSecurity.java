package com.spring.practice.springSecurity.utils;

import com.spring.practice.springSecurity.DTO.BookDto;
import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.service.BookService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BookSecurity {

    private final BookService bookService;


    public BookSecurity(BookService bookService) {
        this.bookService = bookService;
    }

    public boolean isOwnerOfBook(long bookId){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookDto book = bookService.getBookById(bookId);
        return book.getUser().getId().equals(user.getId());
    }
}
