package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Book;
import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private BookRepository bookRepository;

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

    // 添加书籍到购物车的方法
    @Transactional
    public CartItem addBookToCart(Integer userId, Integer bookId, Integer quantity) {
        // 检查书籍是否存在
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            // 如果书籍不存在，返回null或抛出异常
            throw new IllegalArgumentException("Book with id " + bookId + " does not exist.");
        }

        Book book = bookOptional.get();
        Integer singlePrice = book.getPrice(); // 假设Book实体有一个方法来获取价格
        Integer price = singlePrice * quantity;
        // 创建一个新的购物车项
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setBookId(bookId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price); // 设置价格，这里假设价格是从Book实体中获取的
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteAllCartItemsByUserId(Integer userId)
    {
        cartItemRepository.deleteAllByUserId(userId);
    }
}
