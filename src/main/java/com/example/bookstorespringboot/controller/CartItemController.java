package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.model.Order;
import com.example.bookstorespringboot.service.CartItemService;
import com.example.bookstorespringboot.service.UserAuthService;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping
    public ResponseEntity<?> getAllCartItems(HttpServletRequest request) {
//        return ResponseEntity.ok(cartItemService.findAllCartItemsByUserId(1)); // 假定用户ID为1
        try {
            String token = request.getHeader("token");  // 直接从 "token" header 中获取 token，不需要 "Bearer " 前缀
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            // Assuming you have a method to get user ID by email
            Integer userId = userAuthService.getIdByEmail(email);
//            List<Order> orders = orderService.getOrdersByUserId(userId);
            List<CartItem> cartItems = cartItemService.findAllCartItemsByUserId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.addCartItem(cartItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Integer id, @RequestBody CartItem cartItem) {
        cartItem.setId(id);
        return ResponseEntity.ok(cartItemService.updateCartItem(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok().build();
    }
}
