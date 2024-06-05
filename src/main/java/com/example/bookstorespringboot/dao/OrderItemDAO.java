package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.OrderItem;

public interface OrderItemDAO {
    OrderItem save(OrderItem orderItem);
}
