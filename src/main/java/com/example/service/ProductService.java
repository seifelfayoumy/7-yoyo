package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class ProductService extends MainService<Product> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
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

    // Other required methods
}