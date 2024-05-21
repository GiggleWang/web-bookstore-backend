package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteAllByUserId(Integer userId);
    List<CartItem> findAllByUserId(Integer userId);
}
