package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UUID userId;
    private Cart testCart;
    private Product testProduct;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize test data
        userId = UUID.randomUUID();
        testUser = new User(userId, "Test User", new ArrayList<>());
        
        testCart = new Cart();
        testCart.setId(UUID.randomUUID());
        testCart.setUserId(userId);
        testCart.setProducts(new ArrayList<>());
        
        testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
        
        testOrder = new Order();
        testOrder.setId(UUID.randomUUID());
        testOrder.setUserId(userId);
        testOrder.setProducts(new ArrayList<>());
        testOrder.setTotalPrice(0.0);
    }

    // Test Case 1: Test adding a user successfully
    @Test
    void testAddUser_Success() {
        // Arrange
        User inputUser = new User("New User");
        inputUser.setId(null); // Ensure ID is null to test ID generation
        
        when(userRepository.addUser(any(User.class))).thenReturn(inputUser);
        when(cartService.addCart(any(Cart.class))).thenReturn(new Cart());
        
        // Act
        User result = userService.addUser(inputUser);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("New User", result.getName());
        
        // Verify interactions
        verify(userRepository).addUser(any(User.class));
        verify(cartService).addCart(any(Cart.class));
    }

    // Test Case 2: Test adding a user with existing ID
    @Test
    void testAddUser_WithExistingId() {
        // Arrange
        UUID existingId = UUID.randomUUID();
        User inputUser = new User(existingId, "User With ID", new ArrayList<>());
        
        when(userRepository.addUser(any(User.class))).thenReturn(inputUser);
        when(cartService.addCart(any(Cart.class))).thenReturn(new Cart());
        
        // Act
        User result = userService.addUser(inputUser);
        
        // Assert
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("User With ID", result.getName());
        
        // Verify interactions
        verify(userRepository).addUser(inputUser);
        verify(cartService).addCart(any(Cart.class));
    }
    
    // Test Case 3: Test adding a user when cart creation fails
    @Test
    void testAddUser_CartCreationFails() {
        // Arrange
        User inputUser = new User("Failed Cart User");
        inputUser.setId(null);
        
        when(userRepository.addUser(any(User.class))).thenReturn(inputUser);
        when(cartService.addCart(any(Cart.class))).thenReturn(null); // Simulate cart creation failure
        
        // Act
        User result = userService.addUser(inputUser);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Failed Cart User", result.getName());
        
        // Verify interactions
        verify(userRepository).addUser(any(User.class));
        verify(cartService).addCart(any(Cart.class));
    }

    // Test Case 1: Test getting a user by ID
    @Test
    void testGetUserById_ExistingUser() {
        // Arrange
        when(userRepository.getUserById(userId)).thenReturn(testUser);
        
        // Act
        User result = userService.getUserById(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Test User", result.getName());
        
        // Verify interactions
        verify(userRepository).getUserById(userId);
    }

    // Test Case 2: Test getting a user by ID when user doesn't exist
    @Test
    void testGetUserById_NonExistingUser() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(userRepository.getUserById(nonExistingId)).thenReturn(null);
        
        // Act
        User result = userService.getUserById(nonExistingId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(userRepository).getUserById(nonExistingId);
    }
    
    // Test Case 3: Test getting a user by ID with null ID
    @Test
    void testGetUserById_NullId() {
        // Arrange
        UUID nullId = null;
        when(userRepository.getUserById(nullId)).thenReturn(null);
        
        // Act
        User result = userService.getUserById(nullId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(userRepository).getUserById(nullId);
    }

    // Test Case 1: Test getting all users - multiple users
    @Test
    void testGetUsers_MultipleUsers() {
        // Arrange
        ArrayList<User> userList = new ArrayList<>(Arrays.asList(
            new User(UUID.randomUUID(), "User 1", new ArrayList<>()),
            new User(UUID.randomUUID(), "User 2", new ArrayList<>())
        ));
        
        when(userRepository.getUsers()).thenReturn(userList);
        
        // Act
        ArrayList<User> result = userService.getUsers();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("User 1", result.get(0).getName());
        assertEquals("User 2", result.get(1).getName());
        
        // Verify interactions
        verify(userRepository).getUsers();
    }
    
    // Test Case 2: Test getting all users - empty list
    @Test
    void testGetUsers_EmptyList() {
        // Arrange
        ArrayList<User> emptyList = new ArrayList<>();
        when(userRepository.getUsers()).thenReturn(emptyList);
        
        // Act
        ArrayList<User> result = userService.getUsers();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify interactions
        verify(userRepository).getUsers();
    }
    
    // Test Case 3: Test getting all users - null return
    @Test
    void testGetUsers_NullReturn() {
        // Arrange
        when(userRepository.getUsers()).thenReturn(null);
        
        // Act
        ArrayList<User> result = userService.getUsers();
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(userRepository).getUsers();
    }

    // Test Case 6: Test getting orders by user ID - multiple orders
    @Test
    void testGetOrdersByUserId_MultipleOrders() {
        // Arrange
        List<Order> orderList = new ArrayList<>(Arrays.asList(
            new Order(UUID.randomUUID(), userId, 20.0, new ArrayList<>()),
            new Order(UUID.randomUUID(), userId, 30.0, new ArrayList<>())
        ));
        
        when(userRepository.getOrdersByUserId(userId)).thenReturn(orderList);
        
        // Act
        List<Order> result = userService.getOrdersByUserId(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(20.0, result.get(0).getTotalPrice());
        assertEquals(30.0, result.get(1).getTotalPrice());
        
        // Verify interactions
        verify(userRepository).getOrdersByUserId(userId);
    }
    
    // Test Case 16: Test getting orders by user ID - empty order list
    @Test
    void testGetOrdersByUserId_EmptyOrderList() {
        // Arrange
        List<Order> emptyOrderList = new ArrayList<>();
        when(userRepository.getOrdersByUserId(userId)).thenReturn(emptyOrderList);
        
        // Act
        List<Order> result = userService.getOrdersByUserId(userId);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify interactions
        verify(userRepository).getOrdersByUserId(userId);
    }
    
    // Test Case 17: Test getting orders by user ID - null user ID
    @Test
    void testGetOrdersByUserId_NullUserId() {
        // Arrange
        UUID nullUserId = null;
        when(userRepository.getOrdersByUserId(nullUserId)).thenReturn(null);
        
        // Act
        List<Order> result = userService.getOrdersByUserId(nullUserId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(userRepository).getOrdersByUserId(nullUserId);
    }

    // Test Case 7: Test adding an order to a user with products in cart
    @Test
    void testAddOrderToUser_WithProductsInCart() {
        // Arrange
        Cart cart = new Cart(userId);
        cart.setProducts(new ArrayList<>(Arrays.asList(
            new Product(UUID.randomUUID(), "Product 1", 10.0),
            new Product(UUID.randomUUID(), "Product 2", 15.0)
        )));
        
        when(cartService.getCartByUserId(userId)).thenReturn(cart);
        
        // Act
        userService.addOrderToUser(userId);
        
        // Assert
        // Verify interactions
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(orderRepository).addOrder(any(Order.class));
        verify(userRepository).addOrderToUser(eq(userId), any(Order.class));
    }

    // Test Case 8: Test adding an order to a user with empty cart
    @Test
    void testAddOrderToUser_WithEmptyCart() {
        // Arrange
        Cart emptyCart = new Cart(userId);
        
        when(cartService.getCartByUserId(userId)).thenReturn(emptyCart);
        
        // Act
        userService.addOrderToUser(userId);
        
        // Assert
        // Verify interactions
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(orderRepository, never()).addOrder(any(Order.class));
        verify(userRepository, never()).addOrderToUser(any(UUID.class), any(Order.class));
    }

    // Test Case 9: Test adding an order to a user with null cart
    @Test
    void testAddOrderToUser_WithNullCart() {
        // Arrange
        when(cartService.getCartByUserId(userId)).thenReturn(null);
        
        // Act
        userService.addOrderToUser(userId);
        
        // Assert
        // Verify interactions
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(orderRepository, never()).addOrder(any(Order.class));
        verify(userRepository, never()).addOrderToUser(any(UUID.class), any(Order.class));
    }

    // Test Case 10: Test emptying a cart
    @Test
    void testEmptyCart_WithExistingCart() {
        // Arrange
        Cart cart = new Cart(userId);
        cart.setProducts(new ArrayList<>(Arrays.asList(
            new Product(UUID.randomUUID(), "Product 1", 10.0),
            new Product(UUID.randomUUID(), "Product 2", 15.0)
        )));
        
        when(cartService.getCartByUserId(userId)).thenReturn(cart);
        
        // Act
        userService.emptyCart(userId);
        
        // Assert
        assertTrue(cart.getProducts().isEmpty());
        
        // Verify interactions
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(cartRepository).addCart(cart);
    }

    // Test Case 11: Test emptying a null cart
    @Test
    void testEmptyCart_WithNullCart() {
        // Arrange
        when(cartService.getCartByUserId(userId)).thenReturn(null);
        
        // Act
        userService.emptyCart(userId);
        
        // Assert
        // Verify interactions
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(cartRepository, never()).addCart(any(Cart.class));
    }
    
    // Test Case 11.1: Test emptying a cart with null userId
    @Test
    void testEmptyCart_WithNullUserId() {
        // Arrange
        UUID nullUserId = null;
        when(cartService.getCartByUserId(nullUserId)).thenReturn(null);
        
        // Act
        userService.emptyCart(nullUserId);
        
        // Assert
        // Verify interactions
        verify(cartService).getCartByUserId(nullUserId);
        verify(cartRepository, never()).addCart(any(Cart.class));
    }

    // Test Case 12: Test removing an order from a user
    @Test
    void testRemoveOrderFromUser_Success() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        
        // Act
        userService.removeOrderFromUser(userId, orderId);
        
        // Assert
        // Verify interactions
        verify(userRepository).removeOrderFromUser(userId, orderId);
        verify(orderRepository).deleteOrderById(orderId);
    }
    
    // Test Case 12.1: Test removing an order with null user ID
    @Test
    void testRemoveOrderFromUser_NullUserId() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID nullUserId = null;
        
        // Act
        userService.removeOrderFromUser(nullUserId, orderId);
        
        // Assert
        // Verify interactions
        verify(userRepository).removeOrderFromUser(nullUserId, orderId);
        verify(orderRepository).deleteOrderById(orderId);
    }
    
    // Test Case 12.2: Test removing an order with null order ID
    @Test
    void testRemoveOrderFromUser_NullOrderId() {
        // Arrange
        UUID nullOrderId = null;
        
        // Act
        userService.removeOrderFromUser(userId, nullOrderId);
        
        // Assert
        // Verify interactions
        verify(userRepository).removeOrderFromUser(userId, nullOrderId);
        verify(orderRepository).deleteOrderById(nullOrderId);
    }

    // Test Case 13: Test deleting a user by ID
    @Test
    void testDeleteUserById_ExistingUser() {
        // Arrange
        when(userRepository.getUserById(userId)).thenReturn(testUser);
        when(cartService.getCartByUserId(userId)).thenReturn(testCart);
        
        // Act
        userService.deleteUserById(userId);
        
        // Assert
        // Verify interactions
        verify(userRepository).getUserById(userId);
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(cartService).deleteCartById(testCart.getId());
        verify(userRepository).deleteUserById(userId);
    }

    // Test Case 14: Test deleting a user by ID with null cart
    @Test
    void testDeleteUserById_WithNullCart() {
        // Arrange
        when(userRepository.getUserById(userId)).thenReturn(testUser);
        when(cartService.getCartByUserId(userId)).thenReturn(null);
        
        // Act
        userService.deleteUserById(userId);
        
        // Assert
        // Verify interactions
        verify(userRepository).getUserById(userId);
        verify(cartService, atLeastOnce()).getCartByUserId(userId);
        verify(cartService, never()).deleteCartById(any(UUID.class));
        verify(userRepository).deleteUserById(userId);
    }

    // Test Case 15: Test deleting a non-existing user
    @Test
    void testDeleteUserById_NonExistingUser() {
        // Arrange
        when(userRepository.getUserById(userId)).thenReturn(null);
        
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUserById(userId);
        });
        
        assertEquals("User not found", exception.getMessage());
        
        // Verify interactions
        verify(userRepository).getUserById(userId);
        verify(cartService, never()).getCartByUserId(any(UUID.class));
        verify(cartService, never()).deleteCartById(any(UUID.class));
        verify(userRepository, never()).deleteUserById(any(UUID.class));
    }
}