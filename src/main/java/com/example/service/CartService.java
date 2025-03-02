package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        super(cartRepository);
        this.cartRepository = cartRepository;
    }

    // Implement all required methods
    public Cart getCartByUserId(UUID userId) {
        return cartRepository.getCartByUserId(userId);
    }

    public void addProductToCart(UUID cartId, Product product) {

    }

    public void deleteProductFromCart (UUID cartId, Product product) {

    }

    public void deleteCartById(UUID id) {

    }
}