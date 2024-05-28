package com.example.bookstorespringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "cover")
    private String cover;

    @Column(name = "ISBN", nullable = false)
    private String ISBN;

    @Column(name = "left_num", nullable = true, columnDefinition = "int default 0")
    private Integer leftNum;

    @Column(name = "active")
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return this.active;
    }

    // 增加销量的方法
    public void increaseSales(int quantity) {
        if (quantity > 0) {
            this.sales += quantity;
        }
    }

    // 减少库存的方法
    public void decreaseStock(int quantity) {
        if (quantity > 0 && this.leftNum >= quantity) {
            this.leftNum -= quantity;
        } else {
            throw new IllegalArgumentException("库存不足或数量无效");
        }
    }
}