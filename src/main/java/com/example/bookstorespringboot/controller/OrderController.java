package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.*;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.repository.OrderItemRepository;
import com.example.bookstorespringboot.repository.OrderRepository;
import com.example.bookstorespringboot.service.*;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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
    public ResponseEntity<?> getOrdersByToken(HttpServletRequest request,@RequestParam(value = "bookName", required = false) String bookName,
                                              @RequestParam(value = "startDate", required = false) String startDate,
                                              @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            // Assuming you have a method to get user ID by email
            Integer userId = userAuthService.getIdByEmail(email);
            List<Order> orders = orderService.getOrdersByUserId(userId,bookName, startDate, endDate);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    @PostMapping("/api/order")
    public ResponseEntity<?> receiveOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        try {
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            Integer userId = userAuthService.getIdByEmail(email);
            List<OrderRequest.Item> list = orderRequest.getItems();
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
    @GetMapping("/api/admin/order")
    public ResponseEntity<?> getAllOrders(
            @RequestParam(value = "bookName", required = false) String bookName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            List<Order> orders = orderService.getAllOrders(bookName, startDate, endDate);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in processing request: " + e.getMessage());
        }
    }

    @GetMapping("/api/admin/statistics/sales")
    public ResponseEntity<?> getSalesStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            List<SalesStatistics> salesStatistics = orderService.getSalesStatistics(startDate, endDate);
            return ResponseEntity.ok(salesStatistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in processing request: " + e.getMessage());
        }
    }

    @GetMapping("/api/admin/statistics/users")
    public ResponseEntity<?> getUserStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            List<UserStatistics> userStatistics = orderService.getUserStatistics(startDate, endDate);
            return ResponseEntity.ok(userStatistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in processing request: " + e.getMessage());
        }
    }

    @GetMapping("/api/user/statistics/purchase")
    public ResponseEntity<?> getUserPurchaseStatistics(
            HttpServletRequest request,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            // Assuming you have a method to get user ID by email
            Integer userId = userAuthService.getIdByEmail(email);
            UserPurchaseStatistics statistics = orderService.getUserPurchaseStatistics(userId, startDate, endDate);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in processing request: " + e.getMessage());
        }
    }
}