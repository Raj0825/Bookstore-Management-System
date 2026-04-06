package com.example.Bookstore.controller;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserRepository userRepository;

    public RecommendationController(RecommendationService recommendationService, UserRepository userRepository) {
        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getRecommendations(@AuthenticationPrincipal UserDetails userDetails) {

        // Use the full path for your entity to fix the "Incompatible Types" error
        com.example.Bookstore.entity.User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(recommendationService.getPersonalizedRecommendations(user));
    }
}
