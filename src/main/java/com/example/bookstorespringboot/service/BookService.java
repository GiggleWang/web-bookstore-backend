package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findAllBooksAdmin(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findBooksByNameAdmin(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findByActive(true, pageable);
    }

    public Page<Book> findBooksByName(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCaseAndActive(name, true,pageable);
    }

    public Book findBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        book.setActive(true);
        book.setSales(0);
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public Integer getPriceById(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(Book::getPrice).orElse(null);
    }

    @Transactional
    public void increaseSalesAndDecreaseStock(Integer bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("书籍未找到"));

        book.increaseSales(quantity);
        book.decreaseStock(quantity);

        bookRepository.save(book);
    }

    public Book toggleBookStatus(Integer bookId, Boolean active) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("书籍未找到"));
        book.setActive(active);
        return bookRepository.save(book);
    }
}
