package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.CartItemDAO;
import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDAOImpl implements CartItemDAO {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void deleteAllByUserId(Integer userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<CartItem> findAllByUserId(Integer userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteById(Integer id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem findById(Integer id) {
        return cartItemRepository.findById(id).orElse(null);
    }
}
