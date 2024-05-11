package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

}
