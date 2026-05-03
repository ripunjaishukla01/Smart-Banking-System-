package com.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true)
    private String accountNumber;

    private Double balance;

    private String role;

    // 👉 ADD THIS FIELD
    @Column(name = "profile_photo")
    private String profilePhoto;

    public User() {
    }

    public User(String fullName, String email, String password, String accountNumber, Double balance, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getAccountNumber() { return accountNumber; }
    public Double getBalance() { return balance; }
    public String getRole() { return role; }

    // 👉 GETTER
    public String getProfilePhoto() {
        return profilePhoto;
    }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setBalance(Double balance) { this.balance = balance; }
    public void setRole(String role) { this.role = role; }

    // 👉 SETTER
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}