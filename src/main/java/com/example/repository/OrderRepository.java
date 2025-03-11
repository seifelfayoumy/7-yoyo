package com.example.repository;

import com.example.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;
    @Override
    protected String getDataPath() {
        return orderDataPath;
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

    public OrderRepository() {
    }

    public void addOrder(Order newOrder) {
        save(newOrder);
    }

    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = getOrders();
        orders.removeIf(order -> order.getId().equals(orderId));
        saveAll(orders);
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId) {
        ArrayList<Order> orders = getOrders();
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}