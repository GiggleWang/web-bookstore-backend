package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.service.OrderService;
import com.example.bookstorespringboot.service.UserAuthService;
import com.example.bookstorespringboot.service.UserService;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity<String> receiveOrder(@RequestBody String orderData, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();

            // Log order data and return confirmation
            System.out.println("Received order data from user " + email + ":");
            System.out.println(orderData);
            String confirmationMessage = "Order received successfully from " + email;
            System.out.println(confirmationMessage);

            return ResponseEntity.ok(confirmationMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or error in processing order");
        }
    }
}