package com.example.Bookstore.repository;

import com.example.Bookstore.entity.Cart;
import com.example.Bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // This is the "Privacy Filter": Find only items belonging to a specific user
    List<Cart> findByUser(User user);

    // This will be useful later for the "Checkout" feature
    void deleteByUser(User user);
}