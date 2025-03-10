package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        // Implementation
        return productRepository.addProduct(product);
    }

    public ArrayList<Product> getProducts() {
        // Implementation
        return productRepository.getProducts();
    }

    public Product getProductById(UUID productId) {
        // Implementation
        return productRepository.getProductById(productId);
    }

    /**
     * Update a specific product by passing its ID and the new name and new price
     */
    public Product updateProduct(UUID productId, String newName, double newPrice) {
        return productRepository.updateProduct(productId, newName, newPrice);
    }

    /**
     * Apply the given discount to an array list of products' IDs
     * The discount will be given as decimal number (60 means apply 60% discount)
     */
    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        productRepository.applyDiscount(discount, productIds);
    }

    /**
     * Delete a specific product by passing its ID
     */
    public void deleteProductById(UUID productId) {
        productRepository.deleteProductById(productId);
    }
}