package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order addOrder(Integer userId, String address, String receiver, Integer price);

    Page<Order> getAllOrders(String bookName, String startDate, String endDate, Pageable pageable);

    Page<Order> getOrdersByUserId(Integer userId, String bookName, String startDate, String endDate, Pageable pageable);

    List<SalesStatistics> getSalesStatistics(LocalDate startDate, LocalDate endDate);

    List<UserStatistics> getUserStatistics(LocalDate startDate, LocalDate endDate);

    UserPurchaseStatistics getUserPurchaseStatistics(Integer userId, LocalDate startDate, LocalDate endDate);
}
