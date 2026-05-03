package com.bank.service;

import com.bank.dto.*;
import com.bank.entity.User;
import com.bank.repository.UserRepository;
import com.bank.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String generateAccountNumber() {
        return "AC" + (100000 + new Random().nextInt(900000));
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, null, null, null, null, "Email already exists");
        }

        User user = new User(
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                generateAccountNumber(),
                request.getInitialBalance(),
                "USER"
        );

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getAccountNumber(),
                "Registration successful"
        );
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getAccountNumber(),
                "Login successful"
        );
    }
}