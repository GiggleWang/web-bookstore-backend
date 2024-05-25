package com.example.bookstorespringboot.model;

import java.util.Map;

public class UserPurchaseStatistics {
    private Map<String, Long> bookQuantityMap;
    private long totalBooks;
    private double totalAmount;

    public UserPurchaseStatistics(Map<String, Long> bookQuantityMap, long totalBooks, double totalAmount) {
        this.bookQuantityMap = bookQuantityMap;
        this.totalBooks = totalBooks;
        this.totalAmount = totalAmount;
    }

    public Map<String, Long> getBookQuantityMap() {
        return bookQuantityMap;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
