package com.example.model;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Order {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products = new ArrayList<>();
    
    // Default constructor
    public Order() {
    }
    
    // Constructor with userId
    public Order(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.products = new ArrayList<>();
        this.totalPrice = 0.0;
    }
    
    // Full constructor
    public Order(UUID id, UUID userId, List<Product> products, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>();
        this.totalPrice = totalPrice;
    }
    
    // Constructor with parameters in different order (for test compatibility)
    public Order(UUID id, UUID userId, double totalPrice, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>();
        this.totalPrice = totalPrice;
    }
    
    // Getters and setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
