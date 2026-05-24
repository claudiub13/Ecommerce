package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId,
                                          @RequestParam(defaultValue = "1") int quantity,
                                          Principal principal) {
        return ResponseEntity.ok(cartService.addToCart(principal.getName(), productId, quantity));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long itemId, Principal principal) {
        return ResponseEntity.ok(cartService.removeFromCart(principal.getName(), itemId));
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(Principal principal) {
        return ResponseEntity.ok(cartService.calculateTotal(principal.getName()));
    }

    @PutMapping("/decrease/{itemId}")
    public ResponseEntity<Cart> decreaseQuantity(@PathVariable Long itemId, Principal principal) {
        return ResponseEntity.ok(cartService.decreaseQuantity(principal.getName(), itemId));
    }
}