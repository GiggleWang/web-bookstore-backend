package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.BookDAO;
import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Page<Book> findAllBooksAdmin(Pageable pageable) {
        return bookDAO.findAll(pageable);
    }

    @Override
    public Page<Book> findBooksByNameAdmin(String name, Pageable pageable) {
        return bookDAO.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookDAO.findByActive(true, pageable);
    }

    @Override
    public Page<Book> findBooksByName(String name, Pageable pageable) {
        return bookDAO.findByNameContainingIgnoreCaseAndActive(name, true, pageable);
    }

    @Override
    public Book findBookById(Integer id) {
        return bookDAO.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        book.setActive(true);
        book.setSales(0);
        return bookDAO.save(book);
    }

    @Override
    public void deleteBook(Integer id) {
        bookDAO.deleteById(id);
    }

    @Override
    public Integer getPriceById(Integer bookId) {
        Optional<Book> book = bookDAO.findById(bookId);
        return book.map(Book::getPrice).orElse(null);
    }

    @Override
    @Transactional
    public void increaseSalesAndDecreaseStock(Integer bookId, int quantity) {
        Book book = bookDAO.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("书籍未找到"));

        book.increaseSales(quantity);
        book.decreaseStock(quantity);

        bookDAO.save(book);
    }

    @Override
    public Book toggleBookStatus(Integer bookId, Boolean active) {
        Book book = bookDAO.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("书籍未找到"));
        book.setActive(active);
        return bookDAO.save(book);
    }
}
