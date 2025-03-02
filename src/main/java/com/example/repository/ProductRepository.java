package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return null;
    }

    public ProductRepository() {
        super("products.json", Product.class);
    }

    // Implement all required methods
}