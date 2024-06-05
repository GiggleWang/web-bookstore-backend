package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO {
    Order save(Order order);

    Page<Order> findByBookNameAndDateRange(String bookName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Order> findByBookName(String bookName, Pageable pageable);

    Page<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByUserIdAndBookNameAndDateRange(Integer userId, String bookName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Order> findByUserIdAndBookName(Integer userId, String bookName, Pageable pageable);

    Page<Order> findByUserIdAndDateRange(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByUserIdAndOrderDateBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate);
}
