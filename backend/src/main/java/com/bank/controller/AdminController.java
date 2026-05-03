package com.bank.controller;

import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return bankingService.getAllUsers();
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return bankingService.getAllTransactions();
    }
}