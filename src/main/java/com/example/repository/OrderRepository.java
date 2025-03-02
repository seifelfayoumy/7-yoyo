package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return null;
    }

    public OrderRepository() {
        super("orders.json", Order.class);
    }

    public void addOrder(Order newOrder) {
    }

    public void deleteOrderById(UUID orderId) {

    }

    // Implement all required methods
}