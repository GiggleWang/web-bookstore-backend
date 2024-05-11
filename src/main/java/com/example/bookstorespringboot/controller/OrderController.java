package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {

        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> receiveOrder(@RequestBody String orderData) {
        // 接收到订单数据
        System.out.println("Received order data from frontend:");
        System.out.println(orderData);
        String confirmationMessage = "Order received successfully!";
        System.out.println(confirmationMessage);
        return ResponseEntity.ok(confirmationMessage);
    }
}