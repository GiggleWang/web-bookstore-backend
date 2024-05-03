package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findAllCartItemsByUserId(Integer userId) {
        return cartItemRepository.findAll(); // 这里应该添加过滤条件以根据userId获取购物车项目
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer id) {
        cartItemRepository.deleteById(id);
    }
}
