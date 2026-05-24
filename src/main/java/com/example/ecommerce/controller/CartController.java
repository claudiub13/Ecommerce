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

    private String resolveUsername(Principal principal) {
        // dacă e logat → username real, altfel un username „guest”
        if (principal != null) {
            return principal.getName();
        }
        return "guest"; // sau "anon", cum vrei – vezi mai jos și în DB
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {
        String username = resolveUsername(principal);
        return ResponseEntity.ok(cartService.getCart(username));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId,
                                          @RequestParam(defaultValue = "1") int quantity,
                                          Principal principal) {
        String username = resolveUsername(principal);
        return ResponseEntity.ok(cartService.addToCart(username, productId, quantity));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long itemId, Principal principal) {
        String username = resolveUsername(principal);
        return ResponseEntity.ok(cartService.removeFromCart(username, itemId));
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(Principal principal) {
        String username = resolveUsername(principal);
        return ResponseEntity.ok(cartService.calculateTotal(username));
    }

    @PutMapping("/decrease/{itemId}")
    public ResponseEntity<Cart> decreaseQuantity(@PathVariable Long itemId, Principal principal) {
        String username = resolveUsername(principal);
        return ResponseEntity.ok(cartService.decreaseQuantity(username, itemId));
    }
}
