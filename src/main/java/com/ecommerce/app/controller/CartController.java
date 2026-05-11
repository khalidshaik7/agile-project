package com.ecommerce.app.controller;

import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.CartItem;
import com.ecommerce.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * REST Controller for Cart endpoints
 */
@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get user's cart
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add item to cart
     */
    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.addItemToCart(userId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update item quantity in cart
     */
    @PutMapping("/{cartId}/item/{cartItemId}")
    public ResponseEntity<CartItem> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            CartItem cartItem = cartService.updateItemQuantity(cartId, cartItemId, quantity);
            if (cartItem == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Remove item from cart
     */
    @DeleteMapping("/{cartId}/item/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId) {
        try {
            cartService.removeItemFromCart(cartId, cartItemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Clear entire cart
     */
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
