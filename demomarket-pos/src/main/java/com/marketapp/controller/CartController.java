package com.marketapp.controller;

import com.marketapp.model.Cart;
import com.marketapp.model.CartItem;
import com.marketapp.service.CartService;
import com.marketapp.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final SaleService saleService;

    public CartController(CartService cartService, SaleService saleService) {
        this.cartService = cartService;
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String sessionId) {
        return ResponseEntity.ok(cartService.getCart(sessionId));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam String sessionId,
                                          @RequestParam Long productId,
                                          @RequestParam int quantity) {
        cartService.addToCart(sessionId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam String sessionId,
                                               @RequestParam int index) {
        cartService.removeFromCart(sessionId, index);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestParam String sessionId) {
        Cart cart = cartService.getCart(sessionId);
        saleService.recordBulkSales(cart.getItems());
        cartService.clearCart(sessionId);
        return ResponseEntity.ok().build();
    }
}