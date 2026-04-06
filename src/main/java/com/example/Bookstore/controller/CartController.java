package com.example.Bookstore.controller;

import com.example.Bookstore.dto.CartRequest;
import com.example.Bookstore.dto.CheckoutRequest;
import com.example.Bookstore.dto.InvoiceResponse;
import com.example.Bookstore.entity.Cart;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // Add to your own private cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(cartService.addToCart(request, currentUser));
    }

    // View ONLY your cart
    @GetMapping("/my-cart")
    public ResponseEntity<List<Cart>> getMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(cartService.getMyCart(currentUser));
    }

    @PostMapping("/checkout")
    public ResponseEntity<InvoiceResponse> checkout(@RequestBody CheckoutRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        // 1. Get the user
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Call the service (Change String to InvoiceResponse)
        InvoiceResponse invoice = cartService.checkout(user, request);

        // 3. Return the invoice object directly
        return ResponseEntity.ok(invoice);
    }

}