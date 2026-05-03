package com.bank.dto;

public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Double initialBalance;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(Double initialBalance) { this.initialBalance = initialBalance; }
}