package com.example.bookstorespringboot.model;

public class LoginRequest {
    private String email;     // 用户登录时使用的电子邮件地址
    private String password;  // 用户的密码
    private String name;      // 用户的名称cart_items
    private String address;   // 用户的地址
    private String telephone; // 用户的电话号码
    private int type;         // 用户的类型，可能表示用户角色或权限等级

    // 默认构造函数
    public LoginRequest() {
    }

    // 带所有参数的构造函数
    public LoginRequest(String email, String password, String name, String address, String telephone, int type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
