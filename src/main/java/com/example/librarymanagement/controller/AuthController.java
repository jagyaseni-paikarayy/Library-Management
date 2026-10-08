package com.example.librarymanagement.controller;

import com.example.librarymanagement.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username) {

        String token = jwtService.generateToken(username);

        return Map.of(
                "message", "Login successful",
                "token", token
        );
    }
}