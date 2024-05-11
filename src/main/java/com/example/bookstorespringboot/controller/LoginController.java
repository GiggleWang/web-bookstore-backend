package com.example.bookstorespringboot.controller;

import com.example.bookstorespringboot.model.LoginRequest;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest){
        System.out.println("Received email: " + loginRequest.getEmail());
        System.out.println("Received password: " + loginRequest.getPassword());

        return loginService.login(loginRequest);
    }

    @RequestMapping("/api/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}

