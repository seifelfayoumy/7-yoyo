package com.example.repository;

import com.example.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    @Value("${spring.application.productDataPath}")
    private String productDataPath;
    @Override
    protected String getDataPath() {
        return productDataPath;
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    public ProductRepository() {
        // Default constructor
    }
    
    /**
     * Add a new product to the products JSON file
     */
    public Product addProduct(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }
        ArrayList<Product> products = getProducts();
        products.add(product);
        saveAll(products);
        return product;
    }

    /**
     * Get all products from the JSON file
     */
    public ArrayList<Product> getProducts() {
        return findAll();
    }

    /**
     * Get a specific product by ID
     */
    public Product getProductById(UUID productId) {
        ArrayList<Product> products = getProducts();
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Update a product with new name and price
     */
    public Product updateProduct(UUID productId, String newName, double newPrice) {
        ArrayList<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setName(newName);
                product.setPrice(newPrice);
                saveAll(products);
                return product;
            }
        }
        return null; // Product not found
    }

    /**
     * Apply discount to specified products
     */
    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        ArrayList<Product> products = getProducts();
        boolean updated = false;
        
        for (Product product : products) {
            if (productIds.contains(product.getId())) {
                // Apply discount (e.g., 60 means 60% discount, so multiply by 0.4)
                double discountFactor = 1 - (discount / 100.0);
                product.setPrice(product.getPrice() * discountFactor);
                updated = true;
            }
        }
        
        if (updated) {
            saveAll(products);
        }
    }

    /**
     * Delete a product by ID
     */
    public void deleteProductById(UUID productId) {
        ArrayList<Product> products = getProducts();
        products.removeIf(product -> product.getId().equals(productId));
        saveAll(products);
    }
}