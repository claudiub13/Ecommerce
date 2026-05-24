package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    private final String ADMIN_EMAIL = "claudiu@admin.com";

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product,
                                    Authentication auth) {

        if (auth == null || !auth.getName().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403).body("Forbidden");
        }

        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Product product,
                                    Authentication auth) {

        if (auth == null || !auth.getName().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403).body("Forbidden");
        }

        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Authentication auth) {

        if (auth == null || !auth.getName().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403).body("Forbidden");
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Produs șters");
    }
}