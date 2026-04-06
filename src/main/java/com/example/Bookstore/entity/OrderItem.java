package com.example.Bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @Id // This is what was missing!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This makes it auto-increment
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;
    private double priceAtPurchase;
}