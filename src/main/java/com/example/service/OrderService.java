package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService {
    private final OrderRepository orderRepository;
    
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    /**
     * Add a new order to the system
     */
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }
    
    /**
     * Get all orders in the system
     */
    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }
    
    /**
     * Get a specific order by its ID
     */
    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }
    
    /**
     * Delete a specific order by its ID
     */
    public void deleteOrderById(UUID orderId) throws IllegalArgumentException {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found");
        }
        orderRepository.deleteOrderById(orderId);
    }
}
