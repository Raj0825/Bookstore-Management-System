package com.example.Bookstore.service;

import com.example.Bookstore.dto.*;
import com.example.Bookstore.entity.*;
import com.example.Bookstore.exception.*;
import com.example.Bookstore.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CartService(CartRepository cartRepository,
                       BookRepository bookRepository,
                       OrderRepository orderRepository,
                       OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public String addToCart(CartRequest request, User user) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book ID " + request.getBookId() + " not found"));

        if (book.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Only " + book.getQuantity() + " copies left in stock!");
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(request.getQuantity());

        cartRepository.save(cart);
        return "Book added to your cart!";
    }

    public List<Cart> getMyCart(User user) {
        return cartRepository.findByUser(user);
    }

    public double calculateTotal(User user) {
        List<Cart> items = cartRepository.findByUser(user);
        return items.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Unified Checkout: Calculates shipping, saves order, and returns Invoice data.
     */
    @Transactional
    public InvoiceResponse checkout(User user, CheckoutRequest details) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Error: Your cart is empty!");
        }

        // 1. Calculate Shipping Cost
        double shippingCost = details.getShippingMethod().equalsIgnoreCase("Express") ? 100.0 : 40.0;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(details.getShippingAddress());
        order.setCity(details.getCity());
        order.setPinCode(details.getPinCode());
        order.setPhoneNumber(details.getPhoneNumber());
        order.setShippingMethod(details.getShippingMethod());
        order.setShippingCost(shippingCost);
        order.setPaymentMethod(details.getPaymentMethod());

        double subTotal = 0;
        List<OrderItem> orderItemsList = new ArrayList<>();

        for (Cart item : cartItems) {
            Book book = item.getBook();
            if (book.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Error: " + book.getTitle() + " is out of stock!");
            }

            book.setQuantity(book.getQuantity() - item.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(book.getPrice());

            subTotal += book.getPrice() * item.getQuantity();
            orderItemsList.add(orderItem);
        }

        order.setTotalAmount(subTotal + shippingCost);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItemsList);
        cartRepository.deleteAll(cartItems);

        // 2. Map to InvoiceResponse for the Bill
        InvoiceResponse invoice = new InvoiceResponse();
        invoice.setOrderId(order.getId());
        invoice.setDate(order.getOrderDate());
        invoice.setCustomerName(user.getName()); // Ensure User entity has a 'name' field
        invoice.setFullAddress(order.getShippingAddress() + ", " + order.getCity() + " - " + order.getPinCode());
        invoice.setSubTotal(subTotal);
        invoice.setShippingCost(shippingCost);
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setPaymentMethod(order.getPaymentMethod());

        return invoice;
    }

    @Transactional
    public String processDirectPurchase(BuyNowRequest request, User user) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getQuantity() < request.getQuantity()) {
            return "Error: Insufficient stock!";
        }

        book.setQuantity(book.getQuantity() - request.getQuantity());
        bookRepository.save(book);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        // Default shipping for direct purchase
        order.setShippingAddress(request.getShippingAddress());
        order.setCity(request.getCity());
        order.setPinCode(request.getPinCode());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setTotalAmount(book.getPrice() * request.getQuantity() + 40.0); // Adding standard shipping

        orderRepository.save(order);

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setBook(book);
        item.setQuantity(request.getQuantity());
        item.setPriceAtPurchase(book.getPrice());
        orderItemRepository.save(item);

        return "Direct Purchase Successful! Order #" + order.getId();
    }
}