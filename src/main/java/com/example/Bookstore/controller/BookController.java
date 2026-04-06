package com.example.Bookstore.controller;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 1. Centralized Add: Admin adds book to the store catalog
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        // No longer passing 'currentUser' because books belong to the store
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // 2. View All: Replaces 'my-books' because everyone sees the same store
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 3. Centralized Delete: Removes book from the store by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        String result = bookService.deleteBook(id);
        if (result.contains("Error")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // 4. Search: Finds books by title or author
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(query));
    }

    // Endpoint: GET /api/books/category/Horror
    @GetMapping("/category/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getBooksByGenre(genre));
    }
}