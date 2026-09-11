package com.esai.library_api.controller;

import com.esai.library_api.model.User;
import com.esai.library_api.repository.UserRepository;
import com.esai.library_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("error", "Username already taken"));
        }
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("error", "Email already registered"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("TEACHER");
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existing = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtService.generateToken(existing.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}