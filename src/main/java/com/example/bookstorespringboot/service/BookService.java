package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public Integer getPriceById(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(Book::getPrice).orElse(null);
    }

    public List<Book> findBooksByName(String name) {
        // 假设你有一个方法在BookRepository中执行搜索
        return bookRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void increaseSalesAndDecreaseStock(Integer bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("书籍未找到"));

        book.increaseSales(quantity);
        book.decreaseStock(quantity);

        bookRepository.save(book);
    }

}
