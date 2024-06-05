package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.BookDAO;
import com.example.bookstorespringboot.dao.OrderItemDAO;
import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.model.OrderItem;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.service.BookService;
import com.example.bookstorespringboot.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final BookDAO bookDAO;
    private final BookService bookService;
    private final OrderItemDAO orderItemDAO;

    public OrderItemServiceImpl(BookDAO bookDAO, BookService bookService, OrderItemDAO orderItemDAO) {
        this.bookDAO = bookDAO;
        this.bookService = bookService;
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    @Transactional
    public OrderItem addOrderItem(Order order, Integer bookId, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBookId(bookId);
        orderItem.setQuantity(quantity);
        Integer price = bookService.getPriceById(bookId);
        Integer totalPrice = price * quantity;
        orderItem.setPrice(totalPrice);
        return orderItemDAO.save(orderItem);
    }
}
