package com.example.bookstorespringboot.model;

import java.util.List;

public class OrderRequest {
    private String address;
    private String receiver;
    private List<Item> items;

    // 构造函数
    public OrderRequest() {
    }

    public OrderRequest(String address, String receiver, List<Item> items) {
        this.address = address;
        this.receiver = receiver;
        this.items = items;
    }

    // Getter 和 Setter 方法
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // 内部类 Item
    public static class Item {
        private int bookId;
        private int quantity;

        public Item() {
        }

        public Item(int bookId, int quantity) {
            this.bookId = bookId;
            this.quantity = quantity;
        }

        public int getBookId() {
        return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
