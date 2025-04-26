package com.nihir.books_rest_api.service;

import com.nihir.books_rest_api.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean isBookExists(Book book);

    Book create(Book book);

    Optional<Book> getBookById(String isbn);

    List<Book> bookList();

    void deleteBook(String isbn);
}
