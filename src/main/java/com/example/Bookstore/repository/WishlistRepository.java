package com.example.Bookstore.repository;

import com.example.Bookstore.entity.User;
import com.example.Bookstore.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndBookId(User user, int bookId);
    void deleteByUserAndBookId(User user, int bookId);
}