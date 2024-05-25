package com.example.bookstorespringboot.model;

public class UserStatistics {
    private Integer userId;
    private Long totalPurchase;

    public UserStatistics(Integer userId, Long totalPurchase) {
        this.userId = userId;
        this.totalPurchase = totalPurchase;
    }

    // Getters and setters
    // Getters
    public Integer getUserId() {
        return userId;
    }

    public Long getTotalPurchase() {
        return totalPurchase;
    }

    // Setters
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTotalPurchase(Long totalPurchase) {
        this.totalPurchase = totalPurchase;
    }
}