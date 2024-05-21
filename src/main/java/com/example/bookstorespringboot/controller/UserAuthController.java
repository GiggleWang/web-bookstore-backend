package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.LoginRequest;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.Users;
import com.example.bookstorespringboot.service.UserAuthService;
import com.example.bookstorespringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest userAuth) {
        try {
            UserAuth registeredUser = userAuthService.registerUser(
                    userAuth.getEmail(),
                    userAuth.getPassword(),
                    userAuth.getAddress(),
                    userAuth.getName(),
                    userAuth.getTelephone(),
                    userAuth.getType());
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/admin/disable/{userId}")
    public ResponseEntity<?> disableUser(@PathVariable Integer userId) {
        userAuthService.disableUser(userId);
        return ResponseEntity.ok("User disabled successfully");
    }

    @PostMapping("/admin/enable/{userId}")
    public ResponseEntity<?> enableUser(@PathVariable Integer userId) {
        userAuthService.enableUser(userId);
        return ResponseEntity.ok("User enabled successfully");
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserAuth>> getAllUsers() {
        List<UserAuth> users = userAuthService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
