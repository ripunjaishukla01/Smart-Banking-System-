package com.bank.service;

import com.bank.dto.AmountRequest;
import com.bank.dto.TransferRequest;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BankingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> getAccount(String accountNumber) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByAccountNumber(accountNumber).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "Account not found");
            return response;
        }

        response.put("success", true);
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("accountNumber", user.getAccountNumber());
        response.put("balance", user.getBalance());
        response.put("role", user.getRole());
        return response;
    }

    public Map<String, Object> deposit(AmountRequest request) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByAccountNumber(request.getAccountNumber()).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "Account not found");
            return response;
        }

        user.setBalance(user.getBalance() + request.getAmount());
        userRepository.save(user);

        transactionRepository.save(new Transaction(
                "DEPOSIT",
                request.getAmount(),
                request.getAccountNumber(),
                request.getAccountNumber(),
                LocalDateTime.now()
        ));

        response.put("success", true);
        response.put("message", "Deposit successful");
        response.put("balance", user.getBalance());
        return response;
    }

    public Map<String, Object> withdraw(AmountRequest request) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByAccountNumber(request.getAccountNumber()).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "Account not found");
            return response;
        }

        if (user.getBalance() < request.getAmount()) {
            response.put("success", false);
            response.put("message", "Insufficient balance");
            return response;
        }

        user.setBalance(user.getBalance() - request.getAmount());
        userRepository.save(user);

        transactionRepository.save(new Transaction(
                "WITHDRAW",
                request.getAmount(),
                request.getAccountNumber(),
                request.getAccountNumber(),
                LocalDateTime.now()
        ));

        response.put("success", true);
        response.put("message", "Withdraw successful");
        response.put("balance", user.getBalance());
        return response;
    }

    public Map<String, Object> transfer(TransferRequest request) {
        Map<String, Object> response = new HashMap<>();

        User sender = userRepository.findByAccountNumber(request.getSenderAccount()).orElse(null);
        User receiver = userRepository.findByAccountNumber(request.getReceiverAccount()).orElse(null);

        if (sender == null || receiver == null) {
            response.put("success", false);
            response.put("message", "Invalid sender or receiver account");
            return response;
        }

        if (sender.getBalance() < request.getAmount()) {
            response.put("success", false);
            response.put("message", "Insufficient balance");
            return response;
        }

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        transactionRepository.save(new Transaction(
                "TRANSFER",
                request.getAmount(),
                request.getSenderAccount(),
                request.getReceiverAccount(),
                LocalDateTime.now()
        ));

        response.put("success", true);
        response.put("message", "Transfer successful");
        response.put("senderBalance", sender.getBalance());
        return response;
    }

    public List<Transaction> getTransactions(String accountNumber) {
        return transactionRepository.findBySenderAccountOrReceiverAccountOrderByTransactionTimeDesc(accountNumber, accountNumber);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}