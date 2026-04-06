package com.example.Bookstore.service;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.entity.Wishlist;
import com.example.Bookstore.exception.ResourceNotFoundException;
import com.example.Bookstore.repository.BookRepository;
import com.example.Bookstore.repository.WishlistRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.bookRepository = bookRepository;
    }

    // RIGHT: Explicitly telling Java to use your Database Entity
    public String toggleWishlist(int bookId, com.example.Bookstore.entity.User user) {
        Optional<Wishlist> existing = wishlistRepository.findByUserAndBookId(user, bookId);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
            return "Removed from Wishlist";
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);
        wishlistRepository.save(wishlist);

        return "Added to Wishlist";
    }

    public List<Wishlist> getMyWishlist(com.example.Bookstore.entity.User user) {
        return wishlistRepository.findByUser(user);
    }
}