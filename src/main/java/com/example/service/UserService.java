package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService {
    // Dependency Injection Variables
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    // Constructor with required variables for Dependency Injection
    @Autowired
    public UserService(UserRepository userRepository, CartRepository cartRepository,
                       ProductRepository productRepository, OrderRepository orderRepository,
                       CartService cartService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public User addUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        User savedUser = userRepository.addUser(user);

        // Create and associate a new cart with the user
        Cart userCart = new Cart();
        userCart.setId(UUID.randomUUID());
        userCart.setUserId(user.getId());
        userCart.setProducts(new ArrayList<>());
        cartService.addCart(userCart);

        return savedUser;
    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }

    public void addOrderToUser(UUID userId) {
        // Get the user's cart
        Cart userCart = cartService.getCartByUserId(userId);

        if (userCart == null || userCart.getProducts().isEmpty()) {
            return; // Nothing to checkout
        }

        // Create a new order
        Order newOrder = new Order();
        newOrder.setId(UUID.randomUUID());
        newOrder.setUserId(userId);

        // Calculate total price and copy products
        double totalPrice = 0;
        List<Product> orderProducts = new ArrayList<>();

        for (Product product : userCart.getProducts()) {
            orderProducts.add(product);
            totalPrice += product.getPrice();
        }

        newOrder.setProducts(orderProducts);
        newOrder.setTotalPrice(totalPrice);

        // Add order to repository
        orderRepository.addOrder(newOrder);

        // Add order to user
        userRepository.addOrderToUser(userId, newOrder);

        // Empty the cart
        emptyCart(userId);
    }

    public void emptyCart(UUID userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        if (userCart != null) {
            userCart.getProducts().clear();
            cartRepository.addCart(userCart); // Update cart in repository
        }
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
        orderRepository.deleteOrderById(orderId);
    }

    public void deleteUserById(UUID userId) {
        // Check if user exists
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // First delete user's cart
        Cart userCart = cartService.getCartByUserId(userId);
        if (userCart != null) {
            cartService.deleteCartById(userCart.getId());
        }

        // Then delete the user
        userRepository.deleteUserById(userId);
    }
}