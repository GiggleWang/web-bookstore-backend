package com.example.bookstorespringboot.repository;


import com.example.bookstorespringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByActive(Boolean active);
    List<Book> findByNameContainingIgnoreCaseAndActive(String name, Boolean active);
}
