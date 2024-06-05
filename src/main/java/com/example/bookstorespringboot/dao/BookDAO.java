package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookDAO {
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Book> findByActive(Boolean active, Pageable pageable);

    Page<Book> findByNameContainingIgnoreCaseAndActive(String name, Boolean active, Pageable pageable);

    Optional<Book> findById(Integer id);

    Book save(Book book);

    void deleteById(Integer id);
}
