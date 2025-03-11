package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private UUID cartId;
    private UUID userId;
    private Cart testCart;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize test data
        cartId = UUID.randomUUID();
        userId = UUID.randomUUID();
        
        testCart = new Cart();
        testCart.setId(cartId);
        testCart.setUserId(userId);
        testCart.setProducts(new ArrayList<>());
        
        testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
    }

    // Test Case 1: Test adding a cart successfully
    @Test
    void testAddCart_Success() {
        // Arrange
        Cart inputCart = new Cart();
        inputCart.setId(null); // Ensure ID is null to test ID generation
        inputCart.setUserId(userId);
        inputCart.setProducts(new ArrayList<>());
        
        doNothing().when(cartRepository).addCart(any(Cart.class));
        
        // Act
        Cart result = cartService.addCart(inputCart);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userId, result.getUserId());
        
        // Verify interactions
        verify(cartRepository).addCart(any(Cart.class));
    }

    // Test Case 2: Test adding a cart with existing ID
    @Test
    void testAddCart_WithExistingId() {
        // Arrange
        Cart inputCart = new Cart();
        inputCart.setId(cartId);
        inputCart.setUserId(userId);
        inputCart.setProducts(new ArrayList<>());
        
        doNothing().when(cartRepository).addCart(any(Cart.class));
        
        // Act
        Cart result = cartService.addCart(inputCart);
        
        // Assert
        assertNotNull(result);
        assertEquals(cartId, result.getId());
        assertEquals(userId, result.getUserId());
        
        // Verify interactions
        verify(cartRepository).addCart(inputCart);
    }

    // Test Case 3: Test adding a cart with null userId
    @Test
    void testAddCart_WithNullUserId() {
        // Arrange
        Cart inputCart = new Cart();
        inputCart.setId(cartId);
        inputCart.setUserId(null); // Set userId to null
        inputCart.setProducts(new ArrayList<>());
        
        // Act
        Cart result = cartService.addCart(inputCart);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository, never()).addCart(any(Cart.class));
    }

    // Test Case 1: Test getting all carts - multiple carts
    @Test
    void testGetCarts_MultipleCarts() {
        // Arrange
        ArrayList<Cart> cartList = new ArrayList<>(Arrays.asList(
            new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>()),
            new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>())
        ));
        
        when(cartRepository.getCarts()).thenReturn(cartList);
        
        // Act
        ArrayList<Cart> result = cartService.getCarts();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify interactions
        verify(cartRepository).getCarts();
    }
    
    // Test Case 2: Test getting all carts - empty list
    @Test
    void testGetCarts_EmptyList() {
        // Arrange
        ArrayList<Cart> emptyList = new ArrayList<>();
        when(cartRepository.getCarts()).thenReturn(emptyList);
        
        // Act
        ArrayList<Cart> result = cartService.getCarts();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify interactions
        verify(cartRepository).getCarts();
    }
    
    // Test Case 3: Test getting all carts - null return
    @Test
    void testGetCarts_NullReturn() {
        // Arrange
        when(cartRepository.getCarts()).thenReturn(null);
        
        // Act
        ArrayList<Cart> result = cartService.getCarts();
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository).getCarts();
    }

    // Test Case 1: Test getting a cart by ID
    @Test
    void testGetCartById_ExistingCart() {
        // Arrange
        when(cartRepository.getCartById(cartId)).thenReturn(testCart);
        
        // Act
        Cart result = cartService.getCartById(cartId);
        
        // Assert
        assertNotNull(result);
        assertEquals(cartId, result.getId());
        assertEquals(userId, result.getUserId());
        
        // Verify interactions
        verify(cartRepository).getCartById(cartId);
    }

    // Test Case 2: Test getting a cart by ID when cart doesn't exist
    @Test
    void testGetCartById_NonExistingCart() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(cartRepository.getCartById(nonExistingId)).thenReturn(null);
        
        // Act
        Cart result = cartService.getCartById(nonExistingId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository).getCartById(nonExistingId);
    }
    
    // Test Case 3: Test getting a cart by ID with null ID
    @Test
    void testGetCartById_NullId() {
        // Arrange
        UUID nullId = null;
        when(cartRepository.getCartById(nullId)).thenReturn(null);
        
        // Act
        Cart result = cartService.getCartById(nullId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository).getCartById(nullId);
    }

    // Test Case 1: Test getting a cart by user ID
    @Test
    void testGetCartByUserId_ExistingCart() {
        // Arrange
        when(cartRepository.getCartByUserId(userId)).thenReturn(testCart);
        
        // Act
        Cart result = cartService.getCartByUserId(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals(cartId, result.getId());
        assertEquals(userId, result.getUserId());
        
        // Verify interactions
        verify(cartRepository).getCartByUserId(userId);
    }

    // Test Case 2: Test getting a cart by user ID when cart doesn't exist
    @Test
    void testGetCartByUserId_NonExistingCart() {
        // Arrange
        UUID nonExistingUserId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(nonExistingUserId)).thenReturn(null);
        
        // Act
        Cart result = cartService.getCartByUserId(nonExistingUserId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository).getCartByUserId(nonExistingUserId);
    }
    
    // Test Case 3: Test getting a cart by user ID with null user ID
    @Test
    void testGetCartByUserId_NullUserId() {
        // Arrange
        UUID nullUserId = null;
        when(cartRepository.getCartByUserId(nullUserId)).thenReturn(null);
        
        // Act
        Cart result = cartService.getCartByUserId(nullUserId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(cartRepository).getCartByUserId(nullUserId);
    }

    // Test Case 1: Test adding a product to cart - success case
    @Test
    void testAddProductToCart_Success() {
        // Arrange
        doNothing().when(cartRepository).addProductToCart(any(UUID.class), any(Product.class));
        
        // Act
        cartService.addProductToCart(cartId, testProduct);
        
        // Assert
        // Verify interactions
        verify(cartRepository).addProductToCart(cartId, testProduct);
    }
    
    // Test Case 2: Test adding a product to cart with null cart ID
    @Test
    void testAddProductToCart_NullCartId() {
        // Arrange
        UUID nullCartId = null;
        
        // Act
        cartService.addProductToCart(nullCartId, testProduct);
        
        // Assert
        // Verify interactions - should still call repository with null cart ID
        // as validation should be handled at repository level
        verify(cartRepository).addProductToCart(nullCartId, testProduct);
    }
    
    // Test Case 3: Test adding a null product to cart
    @Test
    void testAddProductToCart_NullProduct() {
        // Arrange
        Product nullProduct = null;
        doNothing().when(cartRepository).addProductToCart(any(UUID.class), eq(nullProduct));
        
        // Act
        cartService.addProductToCart(cartId, nullProduct);
        
        // Assert
        // Verify interactions
        verify(cartRepository).addProductToCart(cartId, nullProduct);
    }

    // Test Case 1: Test deleting a product from cart - success case
    @Test
    void testDeleteProductFromCart_Success() {
        // Arrange
        doNothing().when(cartRepository).deleteProductFromCart(any(UUID.class), any(Product.class));
        
        // Act
        cartService.deleteProductFromCart(cartId, testProduct);
        
        // Assert
        // Verify interactions
        verify(cartRepository).deleteProductFromCart(cartId, testProduct);
    }
    
    // Test Case 2: Test deleting a product from cart with null cart ID
    @Test
    void testDeleteProductFromCart_NullCartId() {
        // Arrange
        UUID nullCartId = null;
        
        // Act
        cartService.deleteProductFromCart(nullCartId, testProduct);
        
        // Assert
        // Verify interactions - should still call repository with null cart ID
        // as validation should be handled at repository level
        verify(cartRepository).deleteProductFromCart(nullCartId, testProduct);
    }
    
    // Test Case 3: Test deleting a null product from cart
    @Test
    void testDeleteProductFromCart_NullProduct() {
        // Arrange
        Product nullProduct = null;
        doNothing().when(cartRepository).deleteProductFromCart(any(UUID.class), eq(nullProduct));
        
        // Act
        cartService.deleteProductFromCart(cartId, nullProduct);
        
        // Assert
        // Verify interactions
        verify(cartRepository).deleteProductFromCart(cartId, nullProduct);
    }

    // Test Case 1: Test deleting a cart by ID - success case
    @Test
    void testDeleteCartById_Success() {
        // Arrange
        doNothing().when(cartRepository).deleteCartById(any(UUID.class));
        
        // Act
        cartService.deleteCartById(cartId);
        
        // Assert
        // Verify interactions
        verify(cartRepository).deleteCartById(cartId);
    }
    
    // Test Case 2: Test deleting a cart by ID with null cart ID
    @Test
    void testDeleteCartById_NullCartId() {
        // Arrange
        UUID nullCartId = null;
        
        // Act
        cartService.deleteCartById(nullCartId);
        
        // Assert
        // Verify interactions - should still call repository even with null cart ID
        // as validation should be handled at repository level
        verify(cartRepository).deleteCartById(nullCartId);
    }
    
    // Test Case 3: Test deleting a cart by ID when cart doesn't exist
    @Test
    void testDeleteCartById_NonExistingCart() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        doNothing().when(cartRepository).deleteCartById(nonExistingId);
        
        // Act
        cartService.deleteCartById(nonExistingId);
        
        // Assert
        // Verify interactions
        verify(cartRepository).deleteCartById(nonExistingId);
    }
}