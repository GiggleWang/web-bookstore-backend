package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.CartItem;

import java.util.List;

public interface CartItemDAO {
    void deleteAllByUserId(Integer userId);

    List<CartItem> findAllByUserId(Integer userId);

    CartItem save(CartItem cartItem);

    void deleteById(Integer id);

    CartItem findById(Integer id);
}
