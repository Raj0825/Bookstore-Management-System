package com.example.Bookstore.dto;

import lombok.Data;

@Data
public class CartRequest {
    private int bookId;
    private int quantity;
}