package com.example.bookstorespringboot.repository;


import com.example.bookstorespringboot.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Book> findByActive(Boolean active,Pageable pageable);
    Page<Book> findByNameContainingIgnoreCaseAndActive(String name, Boolean active,Pageable pageable);
    Page<Book> findAll(Pageable pageable);
}
