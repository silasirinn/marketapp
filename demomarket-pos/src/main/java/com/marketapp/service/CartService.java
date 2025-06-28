package com.marketapp.service;

import com.marketapp.model.Cart;
import com.marketapp.model.CartItem;
import com.marketapp.model.Product;
import com.marketapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final ProductRepository productRepository;
    private final Map<String, Cart> carts = new HashMap<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Cart getCart(String sessionId) {
        // Eğer session için cart yoksa oluştur ve kaydet
        return carts.computeIfAbsent(sessionId, k -> new Cart());
    }

    public void addToCart(String sessionId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        Cart cart = getCart(sessionId);
        cart.addItem(new CartItem(product, quantity));
    }

    public void removeFromCart(String sessionId, int index) {
        Cart cart = carts.get(sessionId);
        if (cart != null) {
            cart.removeItem(index);
        }
    }

    public void clearCart(String sessionId) {
        Cart cart = carts.get(sessionId);
        if (cart != null) {
            cart.getItems().clear(); // ✅ sadece içeriği sil
        }
    }
}
