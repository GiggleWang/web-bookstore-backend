package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.model.OrderRequest;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.repository.OrderItemRepository;
import com.example.bookstorespringboot.repository.OrderRepository;
import com.example.bookstorespringboot.service.*;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @GetMapping("/api/order")
    public ResponseEntity<?> getOrdersByToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            // Assuming you have a method to get user ID by email
            Integer userId = userAuthService.getIdByEmail(email);
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    @PostMapping("/api/order")
    public ResponseEntity<?> receiveOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        try {
//            System.out.println("orderRequest");
//            System.out.println(orderRequest.getAddress());
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            Integer userId = userAuthService.getIdByEmail(email);
//            CartItem cartItem = cartItemService.addBookToCart(userId, cartRequest.getBookId(), cartRequest.getQuantity());
            List<OrderRequest.Item> list = orderRequest.getItems();
//            System.out.println(list);
            Integer totalPrice = 0;
            for (OrderRequest.Item item : list) {
                Integer price = bookService.getPriceById(item.getBookId());
                Integer number = item.getQuantity();
                if (price != null) {
                    totalPrice += (price * number);
                } else {
                    // 可以处理书籍价格未找到的情况，例如记录错误、返回错误响应等
//                    System.out.println("未找到ID为 " + item.getBookId() + " 的书籍价格。");
                }
            }
            Order order = orderService.addOrder(userId, orderRequest.getAddress(), orderRequest.getReceiver(), totalPrice);
            for(OrderRequest.Item item : list)
            {
                orderItemService.addOrderItem(order,item.getBookId(),item.getQuantity());
            }
            return ResponseEntity.ok(order);


        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or error in processing order: " + e.getMessage());
        }
    }

    // OrderController.java
    @GetMapping("/api/admin/order")
    public ResponseEntity<?> getAllOrders(HttpServletRequest request) {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error in processing request: " + e.getMessage());
        }
    }

}