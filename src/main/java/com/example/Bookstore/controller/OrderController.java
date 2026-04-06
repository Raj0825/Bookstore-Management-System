package com.example.Bookstore.controller;

//import com.example.Bookstore.dto.BuyNowRequest; // New DTO for Direct Purchase
import com.example.Bookstore.dto.BuyNowRequest;
import com.example.Bookstore.dto.CheckoutRequest; // New DTO for Cart Checkout
import com.example.Bookstore.dto.InvoiceResponse;
import com.example.Bookstore.entity.Order;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.repository.OrderRepository;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderController(CartService cartService, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // 1. Updated Checkout: Now accepts address details via @RequestBody
    @PostMapping("/checkout")
    public ResponseEntity<InvoiceResponse> checkout(@RequestBody CheckoutRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        InvoiceResponse bill = cartService.checkout(user, request);
        return ResponseEntity.ok(bill);
    }

    // 2. View order history (This remains the same)
    @GetMapping("/my-history")
    public ResponseEntity<List<Order>> getOrderHistory(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderRepository.findByUser(user));
    }

    // 3. Updated Buy Now: Now accepts Book ID + Quantity + Address
    @PostMapping("/buy-now")
    public ResponseEntity<String> buyNow(@RequestBody BuyNowRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Passing the combined request to the service
        String result = cartService.processDirectPurchase(request, user);

        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}