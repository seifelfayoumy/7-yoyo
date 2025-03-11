package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
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

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private UUID productId;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize test data
        productId = UUID.randomUUID();
        testProduct = new Product(productId, "Test Product", 10.0);
    }

    // Test Case 1: Test adding a product successfully
    @Test
    void testAddProduct_Success() {
        // Arrange
        Product inputProduct = new Product("New Product", 15.0);
        when(productRepository.addProduct(any(Product.class))).thenReturn(inputProduct);
        
        // Act
        Product result = productService.addProduct(inputProduct);
        
        // Assert
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals(15.0, result.getPrice());
        
        // Verify interactions
        verify(productRepository).addProduct(inputProduct);
    }
    
    // Test Case 2: Test adding a product with null name
    @Test
    void testAddProduct_NullName() {
        // Arrange
        Product inputProduct = new Product(null, 15.0);
        when(productRepository.addProduct(any(Product.class))).thenReturn(inputProduct);
        
        // Act
        Product result = productService.addProduct(inputProduct);
        
        // Assert
        assertNotNull(result);
        assertNull(result.getName());
        assertEquals(15.0, result.getPrice());
        
        // Verify interactions
        verify(productRepository).addProduct(inputProduct);
    }
    
    // Test Case 3: Test adding a product with existing ID
    @Test
    void testAddProduct_WithExistingId() {
        // Arrange
        Product inputProduct = new Product(productId, "Product With ID", 25.0);
        when(productRepository.addProduct(any(Product.class))).thenReturn(inputProduct);
        
        // Act
        Product result = productService.addProduct(inputProduct);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Product With ID", result.getName());
        assertEquals(25.0, result.getPrice());
        
        // Verify interactions
        verify(productRepository).addProduct(inputProduct);
    }

    // Test Case 1: Test getting all products - multiple products
    @Test
    void testGetProducts_MultipleProducts() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>(Arrays.asList(
            new Product(UUID.randomUUID(), "Product 1", 10.0),
            new Product(UUID.randomUUID(), "Product 2", 20.0)
        ));
        
        when(productRepository.getProducts()).thenReturn(productList);
        
        // Act
        ArrayList<Product> result = productService.getProducts();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        
        // Verify interactions
        verify(productRepository).getProducts();
    }
    
    // Test Case 2: Test getting all products - empty list
    @Test
    void testGetProducts_EmptyList() {
        // Arrange
        ArrayList<Product> emptyList = new ArrayList<>();
        when(productRepository.getProducts()).thenReturn(emptyList);
        
        // Act
        ArrayList<Product> result = productService.getProducts();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify interactions
        verify(productRepository).getProducts();
    }
    
    // Test Case 3: Test getting all products - null return
    @Test
    void testGetProducts_NullReturn() {
        // Arrange
        when(productRepository.getProducts()).thenReturn(null);
        
        // Act
        ArrayList<Product> result = productService.getProducts();
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(productRepository).getProducts();
    }

    // Test Case 1: Test getting a product by ID
    @Test
    void testGetProductById_ExistingProduct() {
        // Arrange
        when(productRepository.getProductById(productId)).thenReturn(testProduct);
        
        // Act
        Product result = productService.getProductById(productId);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(10.0, result.getPrice());
        
        // Verify interactions
        verify(productRepository).getProductById(productId);
    }

    // Test Case 2: Test getting a product by ID when product doesn't exist
    @Test
    void testGetProductById_NonExistingProduct() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(productRepository.getProductById(nonExistingId)).thenReturn(null);
        
        // Act
        Product result = productService.getProductById(nonExistingId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(productRepository).getProductById(nonExistingId);
    }
    
    // Test Case 3: Test getting a product by ID with null ID
    @Test
    void testGetProductById_NullId() {
        // Arrange
        UUID nullId = null;
        when(productRepository.getProductById(nullId)).thenReturn(null);
        
        // Act
        Product result = productService.getProductById(nullId);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(productRepository).getProductById(nullId);
    }

    // Test Case 1: Test updating a product
    @Test
    void testUpdateProduct_Success() {
        // Arrange
        String newName = "Updated Product";
        double newPrice = 25.0;
        
        Product updatedProduct = new Product(productId, newName, newPrice);
        when(productRepository.updateProduct(productId, newName, newPrice)).thenReturn(updatedProduct);
        
        // Act
        Product result = productService.updateProduct(productId, newName, newPrice);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newPrice, result.getPrice());
        
        // Verify interactions
        verify(productRepository).updateProduct(productId, newName, newPrice);
    }

    // Test Case 2: Test updating a non-existing product
    @Test
    void testUpdateProduct_NonExistingProduct() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        String newName = "Updated Product";
        double newPrice = 25.0;
        
        when(productRepository.updateProduct(nonExistingId, newName, newPrice)).thenReturn(null);
        
        // Act
        Product result = productService.updateProduct(nonExistingId, newName, newPrice);
        
        // Assert
        assertNull(result);
        
        // Verify interactions
        verify(productRepository).updateProduct(nonExistingId, newName, newPrice);
    }
    
    // Test Case 3: Test updating a product with null name
    @Test
    void testUpdateProduct_NullName() {
        // Arrange
        String nullName = null;
        double newPrice = 30.0;
        
        Product updatedProduct = new Product(productId, nullName, newPrice);
        when(productRepository.updateProduct(productId, nullName, newPrice)).thenReturn(updatedProduct);
        
        // Act
        Product result = productService.updateProduct(productId, nullName, newPrice);
        
        // Assert
        assertNotNull(result);
        assertNull(result.getName());
        assertEquals(newPrice, result.getPrice());
        
        // Verify interactions
        verify(productRepository).updateProduct(productId, nullName, newPrice);
    }

    // Test Case 1: Test applying discount to products
    @Test
    void testApplyDiscount() {
        // Arrange
        double discount = 20.0; // 20% discount
        ArrayList<UUID> productIds = new ArrayList<>(Arrays.asList(
            UUID.randomUUID(),
            UUID.randomUUID()
        ));
        
        doNothing().when(productRepository).applyDiscount(discount, productIds);
        
        // Act
        productService.applyDiscount(discount, productIds);
        
        // Assert
        // Verify interactions
        verify(productRepository).applyDiscount(discount, productIds);
    }

    // Test Case 2: Test applying discount with empty product list
    @Test
    void testApplyDiscount_EmptyProductList() {
        // Arrange
        double discount = 20.0; // 20% discount
        ArrayList<UUID> emptyProductIds = new ArrayList<>();
        
        doNothing().when(productRepository).applyDiscount(discount, emptyProductIds);
        
        // Act
        productService.applyDiscount(discount, emptyProductIds);
        
        // Assert
        // Verify interactions
        verify(productRepository).applyDiscount(discount, emptyProductIds);
    }
    
    // Test Case 3: Test applying discount with negative discount value
    @Test
    void testApplyDiscount_NegativeDiscount() {
        // Arrange
        double negativeDiscount = -10.0; // -10% discount
        ArrayList<UUID> productIds = new ArrayList<>(Arrays.asList(
            UUID.randomUUID(),
            UUID.randomUUID()
        ));
        
        doNothing().when(productRepository).applyDiscount(negativeDiscount, productIds);
        
        // Act
        productService.applyDiscount(negativeDiscount, productIds);
        
        // Assert
        // Verify interactions
        verify(productRepository).applyDiscount(negativeDiscount, productIds);
    }

    // Test Case 1: Test deleting a product by ID
    @Test
    void testDeleteProductById() {
        // Arrange
        doNothing().when(productRepository).deleteProductById(productId);
        
        // Act
        productService.deleteProductById(productId);
        
        // Assert
        // Verify interactions
        verify(productRepository).deleteProductById(productId);
    }
    
    // Test Case 2: Test deleting a product with non-existing ID
    @Test
    void testDeleteProductById_NonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        doNothing().when(productRepository).deleteProductById(nonExistingId);
        
        // Act
        productService.deleteProductById(nonExistingId);
        
        // Assert
        // Verify interactions
        verify(productRepository).deleteProductById(nonExistingId);
    }
    
    // Test Case 3: Test deleting a product with null ID
    @Test
    void testDeleteProductById_NullId() {
        // Arrange
        UUID nullId = null;
        doNothing().when(productRepository).deleteProductById(nullId);
        
        // Act
        productService.deleteProductById(nullId);
        
        // Assert
        // Verify interactions
        verify(productRepository).deleteProductById(nullId);
    }
}