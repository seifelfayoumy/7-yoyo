package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    // The Dependency Injection Variables
    private final OrderService orderService;

    // The Constructor with the required variables mapping the Dependency Injection
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Post Request to add a new Order to the system
     */
    @PostMapping("/")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    /**
     * Get Request to get a specific order by passing its ID in the URL
     */
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    /**
     * Get Request to get all the orders in our system
     */
    @GetMapping("/")
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }

    /**
     * Delete Request to delete a specific order by passing its ID in the URL
     */
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrderById(@PathVariable UUID orderId) {
        try {
            orderService.deleteOrderById(orderId);
            return "Order deleted successfully";
        } catch (IllegalArgumentException e) {
            return "Order not found";
        }
    }
}

