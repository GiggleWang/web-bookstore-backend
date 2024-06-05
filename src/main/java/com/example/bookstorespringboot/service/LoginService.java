package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.controller.ResponseResult;
import com.example.bookstorespringboot.model.LoginRequest;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {
    ResponseResult login(LoginRequest loginRequest);

    @Transactional
    ResponseResult logout();
}
