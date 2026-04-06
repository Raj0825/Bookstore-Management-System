package com.example.Bookstore.repository;

import com.example.Bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // New Search Methods
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreIgnoreCase(String genre);

    // Get 4 random books from the same genre the user likes
    @Query(value = "SELECT * FROM books b WHERE b.genre = :genre AND b.id NOT IN " +
            "(SELECT w.book_id FROM wishlist w WHERE w.user_id = :userId) " +
            "LIMIT 20", nativeQuery = true)
    List<Book> findTopRecommendations(@Param("genre") String genre, @Param("userId") int userId);
}