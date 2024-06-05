package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> findAllCartItemsByUserId(Integer userId);

    CartItem addCartItem(CartItem cartItem);

    CartItem updateCartItem(CartItem cartItem);

    void deleteCartItem(Integer id);

    CartItem addBookToCart(Integer userId, Integer bookId, Integer quantity);

    void deleteAllCartItemsByUserId(Integer userId);
}
