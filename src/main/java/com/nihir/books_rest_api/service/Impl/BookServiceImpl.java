package com.nihir.books_rest_api.service.Impl;

import com.nihir.books_rest_api.domain.Book;
import com.nihir.books_rest_api.domain.BookEntity;
import com.nihir.books_rest_api.repo.BookRepo;
import com.nihir.books_rest_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(final BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepo.existsById(book.getIsbn());
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

    public Optional<Book> getBookById(String isbn) {
        final Optional<BookEntity> foundBook = bookRepo.findById(isbn);
        return foundBook.map(book ->bookEntityToBook(book));
    }

    @Override
    public List<Book> bookList() {
        List<BookEntity> bookEntities = bookRepo.findAll();
        return bookEntities.stream()
                .map(this::bookEntityToBook)
                .toList();
    }

    @Override
    public void deleteBook(String isbn) {
        final Optional<BookEntity> book = bookRepo.findById(isbn);
        if (book.isPresent()) {
            bookRepo.deleteById(isbn);
        }
    }
}
