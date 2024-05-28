package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.*;
import com.example.bookstorespringboot.repository.BookRepository;
import com.example.bookstorespringboot.repository.CartItemRepository;
import com.example.bookstorespringboot.repository.OrderItemRepository;
import com.example.bookstorespringboot.repository.OrderRepository;
import com.example.bookstorespringboot.service.*;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    private CartItemRepository cartItemRepository;


    @GetMapping("/api/order")
    public ResponseEntity<?> getOrdersByToken(HttpServletRequest request,
                                              @RequestParam(value = "bookName", required = false) String bookName,
                                              @RequestParam(value = "startDate", required = false) String startDate,
                                              @RequestParam(value = "endDate", required = false) String endDate,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        try {
            String token = request.getHeader("token");
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            Integer userId = userAuthService.getIdByEmail(email);
            Pageable paging = PageRequest.of(page, size);
            Page<Order> orders = orderService.getOrdersByUserId(userId, bookName, startDate, endDate, paging);

            Map<String, Object> response = new HashMap<>();
            response.put("orders", orders.getContent());
            response.put("currentPage", orders.getNumber());
            response.put("totalItems", orders.getTotalElements());
            response.put("totalPages", orders.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    @GetMapping("/api/admin/order")
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "bookName", required = false) String bookName,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Order> orders = orderService.getAllOrders(bookName, startDate, endDate, paging);

            Map<String, Object> response = new HashMap<>();
            response.put("orders", orders.getContent());
            response.put("currentPage", orders.getNumber());
            response.put("totalItems", orders.getTotalElements());
            response.put("totalPages", orders.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
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