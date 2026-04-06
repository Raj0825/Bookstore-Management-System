package com.example.Bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlist")
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}