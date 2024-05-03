package com.example.bookstorespringboot.model;

public class LoginRequest {
    private String email;    // 用户登录时使用的电子邮件地址
    private String password; // 用户的密码

    // 默认构造函数
    public LoginRequest() {
    }

    // 带参数的构造函数
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
