package com.example.bookstorespringboot.model;

public class SalesStatistics {
    private String bookName;
    private Long sales;

    public SalesStatistics(String bookName, Long sales) {
        this.bookName = bookName;
        this.sales = sales;
    }

    // Getters and setters
    // Getters
    public String getBookName() {
        return bookName;
    }

    public Long getSales() {
        return sales;
    }

    // Setters
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }
}