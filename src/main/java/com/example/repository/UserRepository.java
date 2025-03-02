package com.example.repository;

import com.example.model.Order;
import com.example.model.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {
    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return null;
    }

    public ArrayList<User> getUsers() {
        return readFromFile();
    }

    private ArrayList<User> readFromFile() {
        return null;
    }

    public User getUserById(UUID userId) {
        ArrayList<User> users = getUsers();
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User addUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }

        ArrayList<User> users = getUsers();
        users.add(user);
        writeToFile(users);
        return user;
    }

    private void writeToFile(ArrayList<User> users) {
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        User user = getUserById(userId);
        return user != null ? user.getOrders() : new ArrayList<>();
    }

    public void addOrderToUser(UUID userId, Order order) {
        ArrayList<User> users = getUsers();
        users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    user.getOrders().add(order);
                    writeToFile(users);
                });
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        ArrayList<User> users = getUsers();
        users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    user.setOrders(user.getOrders().stream()
                            .filter(order -> !order.getId().equals(orderId))
                            .collect(java.util.stream.Collectors.toList()));
                    writeToFile(users);
                });
    }

    public void deleteUserById(UUID userId) {
        ArrayList<User> users = getUsers();
        users.removeIf(user -> user.getId().equals(userId));
        writeToFile(users);
    }
}