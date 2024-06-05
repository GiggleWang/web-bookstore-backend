package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.OrderDAO;
import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> findByBookNameAndDateRange(String bookName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByBookNameAndDateRange(bookName, startDate, endDate, pageable);
    }

    @Override
    public Page<Order> findByBookName(String bookName, Pageable pageable) {
        return orderRepository.findByBookName(bookName, pageable);
    }

    @Override
    public Page<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByDateRange(startDate, endDate, pageable);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findByUserIdAndBookNameAndDateRange(Integer userId, String bookName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByUserIdAndBookNameAndDateRange(userId, bookName, startDate, endDate, pageable);
    }

    @Override
    public Page<Order> findByUserIdAndBookName(Integer userId, String bookName, Pageable pageable) {
        return orderRepository.findByUserIdAndBookName(userId, bookName, pageable);
    }

    @Override
    public Page<Order> findByUserIdAndDateRange(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByUserIdAndDateRange(userId, startDate, endDate, pageable);
    }

    @Override
    public Page<Order> findByUserId(Integer userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public List<Order> findByUserIdAndOrderDateBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByUserIdAndOrderDateBetween(userId, startDate, endDate);
    }
}
