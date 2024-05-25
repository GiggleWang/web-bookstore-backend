package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public List<Order> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUserIdWithBooks(userId);
        logger.info("Orders retrieved for userId {}: {}", userId, orders);
        return orders;
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
    @Transactional
    public List<Order> getAllOrders(String bookName, String startDate, String endDate) {
        List<Order> orders = orderRepository.findAll();

        // 过滤书籍名称
        if (bookName != null && !bookName.isEmpty()) {
            orders = orders.stream()
                    .filter(order -> order.getItems().stream()
                            .anyMatch(item -> item.getBookName().toLowerCase().contains(bookName.toLowerCase())))
                    .collect(Collectors.toList());
        }

        // 过滤日期范围
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            orders = orders.stream()
                    .filter(order -> {
                        LocalDateTime orderDate = order.getOrderDate();
                        return (orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end));
                    })
                    .collect(Collectors.toList());
        }

        return orders;
    }

}
