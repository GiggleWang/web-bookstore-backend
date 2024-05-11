package com.example.bookstorespringboot.model;

public class CartRequest {

    // 书籍的ID
    private Integer bookId;

    // 书籍的数量
    private Integer quantity;

    // 构造函数
    public CartRequest() {
    }

    // 带参数的构造函数
    public CartRequest(Integer bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Getter和Setter方法
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}