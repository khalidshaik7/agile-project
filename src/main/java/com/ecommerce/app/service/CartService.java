package com.ecommerce.app.service;

import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.CartItem;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.CartItemRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for Cart operations
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get or create cart for a user
     */
    public Cart getOrCreateCart(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart newCart = new Cart(user.get());
        return cartRepository.save(newCart);
    }

    /**
     * Add item to cart
     */
    public CartItem addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);

        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // Check if product already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update quantity
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new cart item
            cartItem = new CartItem(cart, product.get(), quantity, product.get().getPrice());
        }

        cartItem = cartItemRepository.save(cartItem);

        // Update cart total
        updateCartTotal(cart.getId());

        return cartItem;
    }

    /**
     * Remove item from cart
     */
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isPresent() && cartItem.get().getCart().getId().equals(cartId)) {
            cartItemRepository.deleteById(cartItemId);
            updateCartTotal(cartId);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    /**
     * Update item quantity in cart
     */
    public CartItem updateItemQuantity(Long cartId, Long cartItemId, Integer quantity) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }

        CartItem item = cartItem.get();
        if (!item.getCart().getId().equals(cartId)) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        if (quantity <= 0) {
            removeItemFromCart(cartId, cartItemId);
            return null;
        }

        item.setQuantity(quantity);
        cartItem = Optional.of(cartItemRepository.save(item));

        updateCartTotal(cartId);

        return cartItem.get();
    }

    /**
     * Get cart by user
     */
    public Cart getCartByUserId(Long userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart not found for user");
        }
        return cart.get();
    }

    /**
     * Clear cart
     */
    public void clearCart(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            Cart cartToClear = cart.get();
            cartToClear.getItems().clear();
            cartToClear.setTotalPrice(0.0);
            cartRepository.save(cartToClear);
        }
    }

    /**
     * Update cart total price
     */
    private void updateCartTotal(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            Cart cartToUpdate = cart.get();
            double total = cartToUpdate.getItems().stream()
                    .mapToDouble(CartItem::getSubtotal)
                    .sum();
            cartToUpdate.setTotalPrice(total);
            cartRepository.save(cartToUpdate);
        }
    }
}
