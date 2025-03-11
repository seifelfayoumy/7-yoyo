package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {
    @Value("${spring.application.cartDataPath}")
    private String cartDataPath;
    @Override
    protected String getDataPath() {
        return cartDataPath;
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public CartRepository() {
    }

    // Implement required methods
    public ArrayList<Cart> getCarts() {
        return findAll();
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
        save(userCart);
    }

    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();
        carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .ifPresent(cart -> {
                    cart.getProducts().add(product);
                    saveAll(carts);
                });
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();
        carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .ifPresent(cart -> {
                    cart.getProducts().removeIf(p -> p.getId().equals(product.getId()));
                    saveAll(carts);
                });
    }

    public void deleteCartById(UUID id) {
        ArrayList<Cart> carts = getCarts();
        carts.removeIf(cart -> cart.getId().equals(id));
        saveAll(carts);
    }
}