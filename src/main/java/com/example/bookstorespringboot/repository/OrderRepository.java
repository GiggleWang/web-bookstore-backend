package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.book b WHERE o.userId = :userId AND b.id = i.book.id")
    List<Order> findByUserIdWithBooks(@Param("userId") Integer userId);
}


