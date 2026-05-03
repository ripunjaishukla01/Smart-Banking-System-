package com.bank.controller;

import com.bank.dto.FixedDepositRequest;
import com.bank.entity.FixedDeposit;
import com.bank.service.FixedDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fd")
@CrossOrigin(origins = "*")
public class FixedDepositController {

    @Autowired
    private FixedDepositService fixedDepositService;

    @PostMapping
    public FixedDeposit createFd(@RequestBody FixedDepositRequest request) {
        return fixedDepositService.createFd(request);
    }

    @GetMapping("/{accountNumber}")
    public List<FixedDeposit> getFds(@PathVariable String accountNumber) {
        return fixedDepositService.getFds(accountNumber);
    }
}