package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.*;
import com.example.bookstorespringboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mysql.cj.conf.PropertyKey.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;


@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Transactional
    public Order addOrder(Integer userId,String address,String receiver,Integer price)
    {
        Order order=new Order();
        order.setUserId(userId);
        order.setReceiver(receiver);
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(price);
        return orderRepository.save(order);
    }
    public Page<Order> getAllOrders(String bookName, String startDate, String endDate, Pageable pageable) {
        if (bookName != null && !bookName.isEmpty()) {
            LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate + "T00:00:00") : LocalDateTime.MIN;
            LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate + "T23:59:59") : LocalDateTime.MAX;
            return orderRepository.findByBookNameContaining(bookName, start, end, pageable);
        } else {
            return orderRepository.findAll(pageable);
        }
    }

    public Page<Order> getOrdersByUserId(Integer userId, String bookName, String startDate, String endDate, Pageable pageable) {
        if (bookName != null && !bookName.isEmpty()) {
            LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate + "T00:00:00") : LocalDateTime.MIN;
            LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate + "T23:59:59") : LocalDateTime.MAX;
            return orderRepository.findByUserIdAndBookNameContaining(userId, bookName, start, end, pageable);
        } else {
            return orderRepository.findByUserId(userId, pageable);
        }
    }

    public List<SalesStatistics> getSalesStatistics(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);

        Map<String, Long> salesMap = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getBookName, Collectors.summingLong(OrderItem::getQuantity)));

        return salesMap.entrySet().stream()
                .map(entry -> new SalesStatistics(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getSales(), a.getSales()))
                .collect(Collectors.toList());
    }

    public List<UserStatistics> getUserStatistics(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);

        Map<Integer, Long> userMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getUserId, Collectors.summingLong(Order::getTotalPrice)));

        return userMap.entrySet().stream()
                .map(entry -> new UserStatistics(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getTotalPurchase(), a.getTotalPurchase()))
                .collect(Collectors.toList());
    }

    public UserPurchaseStatistics getUserPurchaseStatistics(Integer userId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByUserIdAndOrderDateBetween(userId, start, end);

        Map<String, Long> bookQuantityMap = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getBookName, Collectors.summingLong(OrderItem::getQuantity)));

        long totalBooks = bookQuantityMap.values().stream().mapToLong(Long::longValue).sum();
        long totalAmount = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToLong(OrderItem::getPrice).sum(); // 直接使用单价

        return new UserPurchaseStatistics(bookQuantityMap, totalBooks, totalAmount);
    }
}
