package com.example.service;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
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

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private UUID orderId;
    private UUID userId;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize test data
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        
        testOrder = new Order();
        testOrder.setId(orderId);
        testOrder.setUserId(userId);
        testOrder.setProducts(new ArrayList<>());
        testOrder.setTotalPrice(0.0);
    }

    // Test Case 1: Test adding an order with valid data
    @Test
    void testAddOrder_ValidData() {
        // Arrange
        Order inputOrder = new Order();
        inputOrder.setId(UUID.randomUUID());
        inputOrder.setUserId(userId);
        inputOrder.setProducts(new ArrayList<>());
        inputOrder.setTotalPrice(0.0);
        
        doNothing().when(orderRepository).addOrder(any(Order.class));
        
        // Act
        orderService.addOrder(inputOrder);
        
        // Assert
        // Verify interactions
        verify(orderRepository).addOrder(inputOrder);
    }
    
    // Test Case 2: Test adding an order with null ID
    @Test
    void testAddOrder_WithNullId() {
        // Arrange
        Order inputOrder = new Order();
        inputOrder.setId(null); // Set ID to null
        inputOrder.setUserId(userId);
        inputOrder.setProducts(new ArrayList<>());
        inputOrder.setTotalPrice(0.0);
        
        doNothing().when(orderRepository).addOrder(any(Order.class));
        
        // Act
        orderService.addOrder(inputOrder);
        
        // Assert
        // Verify interactions
        verify(orderRepository).addOrder(inputOrder);
    }
    
    // Test Case 3: Test adding an order with products
    @Test
    void testAddOrder_WithProducts() {
        // Arrange
        List<Product> products = Arrays.asList(
            new Product(UUID.randomUUID(), "Product 1", 10.0),
            new Product(UUID.randomUUID(), "Product 2", 15.0)
        );
        
        Order inputOrder = new Order();
        inputOrder.setId(UUID.randomUUID());
        inputOrder.setUserId(userId);
        inputOrder.setProducts(products);
        inputOrder.setTotalPrice(25.0);
        
        doNothing().when(orderRepository).addOrder(any(Order.class));
        
        // Act
        orderService.addOrder(inputOrder);
        
        // Assert
        // Verify interactions
        verify(orderRepository).addOrder(inputOrder);
    }

    // Test Case 1: Test getting all orders - multiple orders
    @Test
    void testGetOrders_MultipleOrders() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>(Arrays.asList(
            new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>()),
            new Order(UUID.randomUUID(), UUID.randomUUID(), 20.0, new ArrayList<>())
        ));
        
        when(orderRepository.getOrders()).thenReturn(orderList);
        
        // Act
        ArrayList<Order> result = orderService.getOrders();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10.0, result.get(0).getTotalPrice());
        assertEquals(20.0, result.get(1).getTotalPrice());
        
        // Verify interactions
        verify(orderRepository).getOrders();
    }
    
    // Test Case 2: Test getting all orders - empty repository
    @Test
    void testGetOrders_EmptyRepository() {
        // Arrange
        when(orderRepository.getOrders()).thenReturn(new ArrayList<>());
        
        // Act
        ArrayList<Order> result = orderService.getOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify interactions
        verify(orderRepository).getOrders();
    }
    
    // Test Case 3: Test getting all orders - null return
    @Test
    void testGetOrders_NullReturn() {
        // Arrange
        when(orderRepository.getOrders()).thenReturn(null);
        
        // Act
        ArrayList<Order> result = orderService.getOrders();
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(orderRepository).getOrders();
    }

    // Test Case 1: Test getting an order by ID
    @Test
    void testGetOrderById_ExistingOrder() {
        // Arrange
        when(orderRepository.getOrderById(orderId)).thenReturn(testOrder);
        
        // Act
        Order result = orderService.getOrderById(orderId);
        
        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(0.0, result.getTotalPrice());
        
        // Verify interactions
        verify(orderRepository).getOrderById(orderId);
    }

    // Test Case 2: Test getting an order by ID when order doesn't exist
    @Test
    void testGetOrderById_NonExistingOrder() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(orderRepository.getOrderById(nonExistingId)).thenReturn(null);
        
        // Act
        Order result = orderService.getOrderById(nonExistingId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(orderRepository).getOrderById(nonExistingId);
    }
    
    // Test Case 3: Test getting an order by ID with null ID
    @Test
    void testGetOrderById_NullId() {
        // Arrange
        UUID nullId = null;
        when(orderRepository.getOrderById(nullId)).thenReturn(null);
        
        // Act
        Order result = orderService.getOrderById(nullId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(orderRepository).getOrderById(nullId);
    }

    // Test Case 1: Test deleting an order by ID - existing order
    @Test
    void testDeleteOrderById_ExistingOrder() {
        // Arrange
        when(orderRepository.getOrderById(orderId)).thenReturn(testOrder);
        doNothing().when(orderRepository).deleteOrderById(orderId);
        
        // Act
        orderService.deleteOrderById(orderId);
        
        // Assert
        // Verify interactions
        verify(orderRepository).getOrderById(orderId);
        verify(orderRepository).deleteOrderById(orderId);
    }

    // Test Case 2: Test deleting a non-existing order
    @Test
    void testDeleteOrderById_NonExistingOrder() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(orderRepository.getOrderById(nonExistingId)).thenReturn(null);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrderById(nonExistingId);
        });
        
        assertTrue(exception.getMessage().contains("not found"));
        
        // Verify interactions
        verify(orderRepository).getOrderById(nonExistingId);
        verify(orderRepository, never()).deleteOrderById(any(UUID.class));
    }
    
    // Test Case 3: Test deleting an order with null ID
    @Test
    void testDeleteOrderById_NullOrderId() {
        // Arrange
        UUID nullOrderId = null;
        when(orderRepository.getOrderById(nullOrderId)).thenReturn(null);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrderById(nullOrderId);
        });
        
        assertTrue(exception.getMessage().contains("not found"));
        
        // Verify interactions
        verify(orderRepository).getOrderById(nullOrderId);
        verify(orderRepository, never()).deleteOrderById(any(UUID.class));
    }


}