package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.BookDAO;
import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookDAOImpl implements BookDAO {

    private final BookRepository bookRepository;

    @Autowired
    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Book> findByActive(Boolean active, Pageable pageable) {
        return bookRepository.findByActive(active, pageable);
    }

    @Override
    public Page<Book> findByNameContainingIgnoreCaseAndActive(String name, Boolean active, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCaseAndActive(name, active, pageable);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }
}
