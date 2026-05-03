package com.bank.controller;

import com.bank.dto.BeneficiaryRequest;
import com.bank.entity.Beneficiary;
import com.bank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@CrossOrigin(origins = "*")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping
    public Beneficiary addBeneficiary(@RequestBody BeneficiaryRequest request) {
        return beneficiaryService.addBeneficiary(request);
    }

    @GetMapping("/{accountNumber}")
    public List<Beneficiary> getBeneficiaries(@PathVariable String accountNumber) {
        return beneficiaryService.getBeneficiaries(accountNumber);
    }
}