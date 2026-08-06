package com.atharva.bankingsystem.controller;

import com.atharva.bankingsystem.dto.AccountRequest;
import com.atharva.bankingsystem.dto.AccountResponse;
import com.atharva.bankingsystem.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(){
        List<AccountResponse> accountResponses = accountService.getAllAccounts();
        return ResponseEntity.ok(accountResponses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK')")
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest){
        AccountResponse accountResponse = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long accountId){
        AccountResponse accountResponse = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getAccountByCustomerId(@PathVariable Long customerId){
        List<AccountResponse> accountResponses = accountService.getAllAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accountResponses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK')")
    @PatchMapping("/{accountId}/close")
    public ResponseEntity<AccountResponse> closeAccountById(@PathVariable Long accountId){
        AccountResponse accountResponse = accountService.closeAccount(accountId);
        return ResponseEntity.ok(accountResponse);
    }

}
