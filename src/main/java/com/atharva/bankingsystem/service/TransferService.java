package com.atharva.bankingsystem.service;

import com.atharva.bankingsystem.dto.TransferRequest;
import com.atharva.bankingsystem.dto.TransferResponse;
import com.atharva.bankingsystem.entity.Account;
import com.atharva.bankingsystem.entity.Transaction;
import com.atharva.bankingsystem.entity.Transfer;
import com.atharva.bankingsystem.enums.TransactionStatus;
import com.atharva.bankingsystem.enums.TransactionType;
import com.atharva.bankingsystem.enums.TransferStatus;
import com.atharva.bankingsystem.exception.AccountNotFoundException;
import com.atharva.bankingsystem.exception.InsufficientBalanceException;
import com.atharva.bankingsystem.exception.TransferNotFoundException;
import com.atharva.bankingsystem.repository.AccountRepository;
import com.atharva.bankingsystem.repository.TransactionRepository;
import com.atharva.bankingsystem.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository,
                           TransactionRepository transactionRepository,
                           AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException("From account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException("To account not found"));

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal fromBalanceBefore = fromAccount.getBalance();
        BigDecimal toBalanceBefore = toAccount.getBalance();

        fromAccount.setBalance(fromBalanceBefore.subtract(request.getAmount()));
        toAccount.setBalance(toBalanceBefore.add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction debitTransaction = Transaction.builder()
                .account(fromAccount)
                .type(TransactionType.WITHDRAW)
                .amount(request.getAmount())
                .balanceBefore(fromBalanceBefore)
                .balanceAfter(fromAccount.getBalance())
                .status(TransactionStatus.SUCCESS)
                .build();

        debitTransaction = transactionRepository.save(debitTransaction);

        Transaction creditTransaction = Transaction.builder()
                .account(toAccount)
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .balanceBefore(toBalanceBefore)
                .balanceAfter(toAccount.getBalance())
                .status(TransactionStatus.SUCCESS)
                .build();

        creditTransaction = transactionRepository.save(creditTransaction);

        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(request.getAmount())
                .debitTransaction(debitTransaction)
                .creditTransaction(creditTransaction)
                .status(TransferStatus.SUCCESS)
                .build();

        Transfer savedTransfer = transferRepository.save(transfer);

        return mapToResponse(savedTransfer);
    }

    public TransferResponse getTransferById(Long transferId) {

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found"));

        return mapToResponse(transfer);
    }

    public List<TransferResponse> getTransfersByAccountId(Long accountId) {

        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        List<Transfer> transfers = transferRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

        List<TransferResponse> responses = new ArrayList<>();

        for (Transfer transfer : transfers) {
            responses.add(mapToResponse(transfer));
        }

        return responses;
    }

    @Transactional
    public TransferResponse reverseTransfer(Long transferId) {

        Transfer oldTransfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found"));

        if (oldTransfer.getStatus() == TransferStatus.REVERSED) {
            throw new IllegalStateException("Transfer already reversed.");
        }

        Account fromAccount = oldTransfer.getToAccount();
        Account toAccount = oldTransfer.getFromAccount();

        BigDecimal amount = oldTransfer.getAmount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to reverse transfer.");
        }

        BigDecimal fromBalanceBefore = fromAccount.getBalance();
        BigDecimal toBalanceBefore = toAccount.getBalance();

        fromAccount.setBalance(fromBalanceBefore.subtract(amount));
        toAccount.setBalance(toBalanceBefore.add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction debitTransaction = Transaction.builder()
                .account(fromAccount)
                .type(TransactionType.WITHDRAW)
                .amount(amount)
                .balanceBefore(fromBalanceBefore)
                .balanceAfter(fromAccount.getBalance())
                .status(TransactionStatus.SUCCESS)
                .build();

        debitTransaction = transactionRepository.save(debitTransaction);

        Transaction creditTransaction = Transaction.builder()
                .account(toAccount)
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .balanceBefore(toBalanceBefore)
                .balanceAfter(toAccount.getBalance())
                .status(TransactionStatus.SUCCESS)
                .build();

        creditTransaction = transactionRepository.save(creditTransaction);

        Transfer reverseTransfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .debitTransaction(debitTransaction)
                .creditTransaction(creditTransaction)
                .status(TransferStatus.SUCCESS)
                .build();

        reverseTransfer = transferRepository.save(reverseTransfer);

        oldTransfer.setStatus(TransferStatus.REVERSED);
        transferRepository.save(oldTransfer);

        return mapToResponse(reverseTransfer);
    }

    private TransferResponse mapToResponse(Transfer transfer) {

        return TransferResponse.builder()
                .id(transfer.getId())
                .fromAccountId(transfer.getFromAccount().getId())
                .toAccountId(transfer.getToAccount().getId())
                .amount(transfer.getAmount())
                .status(transfer.getStatus())
                .debitTransactionId(transfer.getDebitTransaction().getId())
                .creditTransactionId(transfer.getCreditTransaction().getId())
                .createdAt(transfer.getCreatedAt())
                .build();
    }
}
