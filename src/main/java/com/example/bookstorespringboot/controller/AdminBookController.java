package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.model.BookStatusRequest;
import com.example.bookstorespringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookService bookService;

    @Autowired
    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Book> pageBooks;

            if (name != null && !name.isEmpty()) {
                pageBooks = bookService.findBooksByNameAdmin(name, paging);
            } else {
                pageBooks = bookService.findAllBooksAdmin(paging);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("books", pageBooks.getContent());
            response.put("currentPage", pageBooks.getNumber());
            response.put("totalItems", pageBooks.getTotalElements());
            response.put("totalPages", pageBooks.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Book book = bookService.findBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        Book existingBook = bookService.findBookById(id);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setDescription(book.getDescription());
        existingBook.setCover(book.getCover());
        existingBook.setISBN(book.getISBN());
        existingBook.setLeftNum(book.getLeftNum());
        return ResponseEntity.ok(bookService.saveBook(existingBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        if (bookService.findBookById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Book> toggleBookStatus(@PathVariable Integer id, @RequestBody BookStatusRequest request) {
        Book updatedBook = bookService.toggleBookStatus(id, request.getActive());
        return ResponseEntity.ok(updatedBook);
    }
}
