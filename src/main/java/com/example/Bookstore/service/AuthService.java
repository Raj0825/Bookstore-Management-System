package com.example.Bookstore.service;

import com.example.Bookstore.dto.LoginRequest;
import com.example.Bookstore.dto.RegisterRequest;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection (Best Practice!)
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {
        // 1. Check if the email is already taken
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Error: Email is already in use!";
        }

        // 2. Create a new blank User entity and fill it with data from the request
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        // 3. Hash the password for maximum security
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("ROLE_USER");

        // 4. Save the new user to the MySQL database
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) return "Error: User not found!";
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) return "Error: Invalid password!";

        // Return role along with success so UI can use it
        return "Login successful|" + user.getRole();
    }
}