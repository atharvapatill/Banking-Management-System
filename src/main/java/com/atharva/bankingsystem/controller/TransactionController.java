package com.atharva.bankingsystem.controller;

import com.atharva.bankingsystem.dto.TransactionRequest;
import com.atharva.bankingsystem.dto.TransactionResponse;
import com.atharva.bankingsystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.deposit(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.withdraw(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long transactionId) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionResponse> transactionResponses = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponses);
    }
}