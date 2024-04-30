package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
