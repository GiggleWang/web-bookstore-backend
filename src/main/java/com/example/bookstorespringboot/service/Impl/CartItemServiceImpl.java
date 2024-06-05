package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.BookDAO;
import com.example.bookstorespringboot.dao.CartItemDAO;
import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDAO cartItemDAO;

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<CartItem> findAllCartItemsByUserId(Integer userId) {
        return cartItemDAO.findAllByUserId(userId);
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemDAO.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemDAO.save(cartItem);
    }

    @Override
    public void deleteCartItem(Integer id) {
        cartItemDAO.deleteById(id);
    }

    @Override
    @Transactional
    public CartItem addBookToCart(Integer userId, Integer bookId, Integer quantity) {
        Optional<Book> bookOptional = bookDAO.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("Book with id " + bookId + " does not exist.");
        }

        Book book = bookOptional.get();
        Integer singlePrice = book.getPrice();
        Integer price = singlePrice * quantity;

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setBookId(bookId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price);
        return cartItemDAO.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteAllCartItemsByUserId(Integer userId) {
        cartItemDAO.deleteAllByUserId(userId);
    }
}
