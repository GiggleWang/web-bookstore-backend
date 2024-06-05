package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.OrderDAO;
import com.example.bookstorespringboot.model.*;
import com.example.bookstorespringboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    @Transactional
    public Order addOrder(Integer userId, String address, String receiver, Integer price) {
        Order order = new Order();
        order.setUserId(userId);
        order.setReceiver(receiver);
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(price);
        return orderDAO.save(order);
    }

    @Override
    public Page<Order> getAllOrders(String bookName, String startDate, String endDate, Pageable pageable) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDateTime.parse(startDate + "T00:00:00", formatter);
        }
        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDateTime.parse(endDate + "T23:59:59", formatter);
        }

        if (bookName != null && !bookName.isEmpty() && start != null && end != null) {
            return orderDAO.findByBookNameAndDateRange(bookName, start, end, pageable);
        } else if (bookName != null && !bookName.isEmpty()) {
            return orderDAO.findByBookName(bookName, pageable);
        } else if (start != null && end != null) {
            return orderDAO.findByDateRange(start, end, pageable);
        } else {
            return orderDAO.findAll(pageable);
        }
    }

    @Override
    public Page<Order> getOrdersByUserId(Integer userId, String bookName, String startDate, String endDate, Pageable pageable) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDateTime.parse(startDate + "T00:00:00", formatter);
        }
        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDateTime.parse(endDate + "T23:59:59", formatter);
        }

        if (bookName != null && !bookName.isEmpty() && start != null && end != null) {
            return orderDAO.findByUserIdAndBookNameAndDateRange(userId, bookName, start, end, pageable);
        } else if (bookName != null && !bookName.isEmpty()) {
            return orderDAO.findByUserIdAndBookName(userId, bookName, pageable);
        } else if (start != null && end != null) {
            return orderDAO.findByUserIdAndDateRange(userId, start, end, pageable);
        } else {
            return orderDAO.findByUserId(userId, pageable);
        }
    }

    @Override
    public List<SalesStatistics> getSalesStatistics(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderDAO.findByOrderDateBetween(start, end);

        Map<String, Long> salesMap = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getBookName, Collectors.summingLong(OrderItem::getQuantity)));

        return salesMap.entrySet().stream()
                .map(entry -> new SalesStatistics(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getSales(), a.getSales()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatistics> getUserStatistics(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderDAO.findByOrderDateBetween(start, end);

        Map<Integer, Long> userMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getUserId, Collectors.summingLong(Order::getTotalPrice)));

        return userMap.entrySet().stream()
                .map(entry -> new UserStatistics(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getTotalPurchase(), a.getTotalPurchase()))
                .collect(Collectors.toList());
    }

    @Override
    public UserPurchaseStatistics getUserPurchaseStatistics(Integer userId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderDAO.findByUserIdAndOrderDateBetween(userId, start, end);

        Map<String, Long> bookQuantityMap = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getBookName, Collectors.summingLong(OrderItem::getQuantity)));

        long totalBooks = bookQuantityMap.values().stream().mapToLong(Long::longValue).sum();
        long totalAmount = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToLong(OrderItem::getPrice).sum();

        return new UserPurchaseStatistics(bookQuantityMap, totalBooks, totalAmount);
    }
}
