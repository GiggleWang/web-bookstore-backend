package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.model.OrderItem;

public interface OrderItemService {
    OrderItem addOrderItem(Order order, Integer bookId, Integer quantity);
}
