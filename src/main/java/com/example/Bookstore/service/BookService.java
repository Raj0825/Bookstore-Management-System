package com.example.Bookstore.service;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 1. Add Book (Admin only logic is handled in SecurityConfig)
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // 2. View All Books for the store catalog
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 3. Search by Title
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // 4. Search by Author
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // 5. Delete Book by ID
    public String deleteBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return "Book deleted successfully!";
        }
        return "Error: Book not found.";
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }
}