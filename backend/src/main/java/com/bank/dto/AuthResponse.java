package com.bank.dto;

public class AuthResponse {
    private String token;
    private String fullName;
    private String email;
    private String role;
    private String accountNumber;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String token, String fullName, String email, String role, String accountNumber, String message) {
        this.token = token;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.accountNumber = accountNumber;
        this.message = message;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}