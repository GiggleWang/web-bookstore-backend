package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookService {

    Page<Book> findAllBooksAdmin(Pageable pageable);

    Page<Book> findBooksByNameAdmin(String name, Pageable pageable);

    Page<Book> findAllBooks(Pageable pageable);

    Page<Book> findBooksByName(String name, Pageable pageable);

    Book findBookById(Integer id);

    Book saveBook(Book book);

    void deleteBook(Integer id);

    Integer getPriceById(Integer bookId);

    @Transactional
    void increaseSalesAndDecreaseStock(Integer bookId, int quantity);

    Book toggleBookStatus(Integer bookId, Boolean active);
}
