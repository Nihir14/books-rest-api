package com.nihir.books_rest_api.controller;

import com.nihir.books_rest_api.domain.Book;
import com.nihir.books_rest_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping ("/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final boolean isBookExists = bookService.isBookExists(book);
        final Book createdBook = bookService.create(book);
        if (createdBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(createdBook, isBookExists ? HttpStatus.OK : HttpStatus.CREATED);
    }

    @GetMapping ("/books/{isbn}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable final String isbn) {
        final Optional<Book> book = bookService.getBookById(isbn);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping ("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        final List<Book> books = bookService.bookList();
        if (books == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping ("/books/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable final String isbn) {
        final Optional<Book> book = bookService.getBookById(isbn);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
