package com.nihir.books_rest_api.repo;

import com.nihir.books_rest_api.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, String> {
}
