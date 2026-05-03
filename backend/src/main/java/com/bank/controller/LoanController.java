package com.bank.controller;

import com.bank.dto.LoanRequestDto;
import com.bank.entity.LoanRequest;
import com.bank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public LoanRequest applyLoan(@RequestBody LoanRequestDto dto) {
        return loanService.createLoanRequest(dto);
    }

    @GetMapping("/{accountNumber}")
    public List<LoanRequest> getLoans(@PathVariable String accountNumber) {
        return loanService.getLoansByAccount(accountNumber);
    }
}