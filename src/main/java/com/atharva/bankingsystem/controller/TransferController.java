package com.atharva.bankingsystem.controller;

import com.atharva.bankingsystem.dto.TransferRequest;
import com.atharva.bankingsystem.dto.TransferResponse;
import com.atharva.bankingsystem.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest){
        TransferResponse transferResponse = transferService.transfer(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/{transferId}")
    public ResponseEntity<TransferResponse> getTransferById(@PathVariable Long transferId){
        TransferResponse transferResponse = transferService.getTransferById(transferId);
        return ResponseEntity.ok(transferResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransferResponse>> getTransferByAccountId(@PathVariable Long accountId){
        List<TransferResponse> transferResponses = transferService.getTransfersByAccountId(accountId);
        return ResponseEntity.ok(transferResponses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/reverse/{transferId}")
    public ResponseEntity<TransferResponse> reverseTransfer(@PathVariable Long transferId){
        TransferResponse transferResponse = transferService.reverseTransfer(transferId);
        return ResponseEntity.ok(transferResponse);
    }
}
