package com.example.Bookstore.dto;

import lombok.Data;

@Data
public class BuyNowRequest {
    private int bookId;
    private int quantity;
    private String shippingAddress;
    private String city;
    private String PinCode;
    private String phoneNumber;
}