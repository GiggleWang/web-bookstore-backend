package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.CartItem;
import com.example.bookstorespringboot.model.CartRequest;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Integer userId = userAuthService.getIdByEmail(email);
            List<CartItem> cartItems = cartItemService.findAllCartItemsByUserId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> addCartItem(@RequestBody CartRequest cartRequest, HttpServletRequest request) {
        try {
            String token = request.getHeader("token");  // 获取token
            Claims claims = JwtUtil.parseJWT(token);     // 解析JWT
            String email = claims.getSubject();         // 获取email
            Integer userId = userAuthService.getIdByEmail(email);
            if (userId == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            CartItem cartItem = cartItemService.addBookToCart(userId, cartRequest.getBookId(), cartRequest.getQuantity());

            if (cartItem == null) {
                return ResponseEntity.status(400).body("Failed to add item to cart.");
            }
            return ResponseEntity.ok(cartItem);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error adding item to cart", e);
            return ResponseEntity.status(500).body("Internal Server Error.");
        }
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

    @PostMapping("/clean")
    public ResponseEntity<?> cleanAllCartItems(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();
            Integer userId = userAuthService.getIdByEmail(email);

            if (userId == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            cartItemService.deleteAllCartItemsByUserId(userId);
            return ResponseEntity.ok().body("Cart has been successfully cleaned.");

        } catch (Exception e) {

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error cleaning cart", e);
            return ResponseEntity.status(500).body("Internal Server Error.");
        }
    }
}
