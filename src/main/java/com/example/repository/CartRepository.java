package com.example.repository;

import com.example.model.Cart;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {
    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return null;
    }

    public CartRepository() {
        super("carts.json", Cart.class);
    }

    // Implement required methods
    public ArrayList<Cart> getCarts() {
        return readFromFile();
    }

    public Cart getCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();
        return carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .orElse(null);
    }

    public Cart getCartByUserId(UUID userId) {
        ArrayList<Cart> carts = getCarts();
        return carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addCart(Cart userCart) {

    }

    // Other required methods
}