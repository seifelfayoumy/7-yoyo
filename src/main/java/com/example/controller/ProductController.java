package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    // The Dependency Injection Variables
    private final ProductService productService;

    // The Constructor with the required variables mapping the Dependency Injection
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Post Request to add a new Product to the system
     */
    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * Get Request to get all the products from the system
     */
    @GetMapping("/")
    public ArrayList<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * Get Request to get a specific product by passing its ID in the URL
     */
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    /**
     * Put Request to update the product by passing its ID in the URL with specific body in the request body
     */
    @PutMapping("/update/{productId}")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody Map<String, Object> body) {
        String newName = (String) body.get("name");
        double newPrice = Double.parseDouble(body.get("price").toString());
        return productService.updateProduct(productId, newName, newPrice);
    }

    /**
     * Put Request that takes the discount amount in percentage and a list of product Ids
     * It should update the price of these products
     */
    @PutMapping("/applyDiscount")
    public String applyDiscount(@RequestParam double discount, @RequestBody ArrayList<UUID> productIds) {
        productService.applyDiscount(discount, productIds);
        return "Discount applied successfully";
    }

    /**
     * Delete Request to delete a specific product by passing its ID in the URL
     */
    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable UUID productId) {
        productService.deleteProductById(productId);
        return "Product deleted successfully";
    }
}