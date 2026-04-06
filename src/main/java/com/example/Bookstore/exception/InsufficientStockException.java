package com.example.Bookstore.exception;//package com.example.Bookstore.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}