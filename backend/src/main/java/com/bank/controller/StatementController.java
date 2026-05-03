
package com.bank.controller;

import com.bank.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statements")
@CrossOrigin(origins = "*")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @GetMapping("/{accountNumber}")
    public Map<String, Object> getStatement(@PathVariable String accountNumber,
                                            @RequestParam String month) {
        return statementService.getMonthlyStatement(accountNumber, month);
    }
}