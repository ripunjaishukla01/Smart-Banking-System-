package com.bank.controller;

import com.bank.dto.AmountRequest;
import com.bank.dto.TransferRequest;
import com.bank.entity.Transaction;
import com.bank.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banking")
@CrossOrigin(origins = "*")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/account/{accountNumber}")
    public Map<String, Object> getAccount(@PathVariable String accountNumber) {
        return bankingService.getAccount(accountNumber);
    }

    @PostMapping("/deposit")
    public Map<String, Object> deposit(@RequestBody AmountRequest request) {
        return bankingService.deposit(request);
    }

    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@RequestBody AmountRequest request) {
        return bankingService.withdraw(request);
    }

    @PostMapping("/transfer")
    public Map<String, Object> transfer(@RequestBody TransferRequest request) {
        return bankingService.transfer(request);
    }

    @GetMapping("/transactions/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return bankingService.getTransactions(accountNumber);
    }
}