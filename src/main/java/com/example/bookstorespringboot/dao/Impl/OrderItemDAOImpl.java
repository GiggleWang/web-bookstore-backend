package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.OrderItemDAO;
import com.example.bookstorespringboot.model.OrderItem;
import com.example.bookstorespringboot.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemDAOImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
