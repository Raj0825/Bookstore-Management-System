package com.example.Bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double totalAmount;
    private LocalDateTime orderDate;

    // Add these fields to your existing Order entity
    private String shippingAddress;
    private String phoneNumber;
    private String city;
    private String pinCode;

    private String shippingMethod; // e.g., "Standard", "Express"
    private double shippingCost;
    private String paymentMethod;  // e.g., "UPI", "Credit Card", "COD"
    private String orderStatus;    // e.g., "Pending", "Paid", "Shipped"
}