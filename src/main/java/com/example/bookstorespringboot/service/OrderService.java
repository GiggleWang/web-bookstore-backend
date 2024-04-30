package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

}
