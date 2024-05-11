package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.model.OrderItem;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

@Service
public class OrderItemService {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(BookRepository bookRepository, BookService bookService, OrderItemRepository orderItemRepository) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderItem addOrderItem(Order order, Integer bookId, Integer quantity)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBookId(bookId);
        orderItem.setQuantity(quantity);
        Integer price = bookService.getPriceById(bookId);
        Integer totalPrice = price * quantity;
        orderItem.setPrice(totalPrice);
        return orderItemRepository.save(orderItem);
    }
}
