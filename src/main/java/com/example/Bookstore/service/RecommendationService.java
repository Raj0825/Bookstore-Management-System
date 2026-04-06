package com.example.Bookstore.service;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.entity.Wishlist;
import com.example.Bookstore.repository.BookRepository;
import com.example.Bookstore.repository.WishlistRepository;
import com.example.Bookstore.entity.User; // KEEP THIS ONE
// DELETE THE SPRING SECURITY USER IMPORT FROM HERE
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;

    public RecommendationService(WishlistRepository wishlistRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getPersonalizedRecommendations(User user) {
        List<Wishlist> wishlist = wishlistRepository.findByUser(user);

        if (wishlist.isEmpty()) {
            return bookRepository.findAll().stream().limit(10).toList();
        }

        String favoriteGenre = wishlist.get(wishlist.size() - 1).getBook().getGenre();

        return bookRepository.findTopRecommendations(favoriteGenre, user.getId());
    }
}