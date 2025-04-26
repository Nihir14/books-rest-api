package com.nihir.books_rest_api.service.Impl;

import com.nihir.books_rest_api.domain.Book;
import com.nihir.books_rest_api.domain.BookEntity;
import com.nihir.books_rest_api.repo.BookRepo;
import com.nihir.books_rest_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(final BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book create(Book book) {
        BookEntity bookEntity = bookToBookEntity(book);
        BookEntity savedBookEntity = bookRepo.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    private Book bookEntityToBook(BookEntity savedBookEntity) {
        return Book.builder()
                .isbn(savedBookEntity.getIsbn())
                .author(savedBookEntity.getAuthor())
                .title(savedBookEntity.getTitle())
                .build();
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
    }
}
