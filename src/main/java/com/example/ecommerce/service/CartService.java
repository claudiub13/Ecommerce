package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private Cart getOrCreateCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    public Cart getCart(String username) {
        return getOrCreateCart(username);
    }

    public Cart addToCart(String username, Long productId, int quantity) {
        Cart cart = getOrCreateCart(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produs negăsit"));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
        return cartRepository.findById(cart.getId()).get();
    }

    public Cart removeFromCart(String username, Long itemId) {
        Cart cart = getOrCreateCart(username);
        cartItemRepository.deleteById(itemId);
        return cartRepository.findById(cart.getId()).get();
    }

    public double calculateTotal(String username) {
        Cart cart = getOrCreateCart(username);
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public Cart decreaseQuantity(String username, Long itemId) {
        Cart cart = getOrCreateCart(username);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item negăsit"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        } else {
            cartItemRepository.deleteById(itemId);
        }
        return cartRepository.findById(cart.getId()).get();
    }
}