package com.atharva.bankingsystem.service;

import com.atharva.bankingsystem.dto.TransactionRequest;
import com.atharva.bankingsystem.dto.TransactionResponse;
import com.atharva.bankingsystem.entity.Account;
import com.atharva.bankingsystem.entity.Transaction;
import com.atharva.bankingsystem.enums.TransactionStatus;
import com.atharva.bankingsystem.exception.AccountNotFoundException;
import com.atharva.bankingsystem.exception.InsufficientBalanceException;
import com.atharva.bankingsystem.exception.TransactionNotFoundException;
import com.atharva.bankingsystem.repository.AccountRepository;
import com.atharva.bankingsystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponse deposit(TransactionRequest transactionRequest){
            Optional<Account> account = accountRepository.findById(transactionRequest.getAccountId());

            if(account.isEmpty()){
                throw new AccountNotFoundException("Account Not Found");
            }

        BigDecimal accountBalance = account.get().getBalance();
        BigDecimal newAccountBalance = accountBalance.add(transactionRequest.getAmount());

        account.get().setBalance(newAccountBalance);
        accountRepository.save(account.get());

        Transaction transaction = Transaction
                .builder()
                .account(account.get())
                .type(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .balanceBefore(accountBalance)
                .balanceAfter(newAccountBalance)
                .status(TransactionStatus.SUCCESS)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponse withdraw(TransactionRequest transactionRequest){
        Optional<Account> account = accountRepository.findById(transactionRequest.getAccountId());

        if(account.isEmpty()){
            throw new AccountNotFoundException("Account Not Found");
        }
        int sufficientBalance = account.get().getBalance().compareTo(transactionRequest.getAmount());

        if(sufficientBalance < 0){
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        BigDecimal accountBalance = account.get().getBalance();

        BigDecimal newAccountBalance =accountBalance.subtract(transactionRequest.getAmount());

        account.get().setBalance(newAccountBalance);
        accountRepository.save(account.get());

        Transaction transaction = Transaction
                .builder()
                .account(account.get())
                .type(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .balanceBefore(accountBalance)
                .balanceAfter(newAccountBalance)
                .status(TransactionStatus.SUCCESS)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    public TransactionResponse getTransactionById(Long transactionId){
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if(transaction.isEmpty()){
            throw new TransactionNotFoundException("Transaction Not Found");
        }

        return mapToResponse(transaction.get());
    }

    public List<TransactionResponse> getTransactionsByAccountId(Long accountId){
        Optional<Account> account  = accountRepository.findById(accountId);

        if (account.isEmpty()){
            throw new AccountNotFoundException("Account Not Found");
        }

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for (Transaction transaction: transactions){
            transactionResponses.add(mapToResponse(transaction));
        }

        return transactionResponses;
    }

    // Helper Function
    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .balanceBefore(transaction.getBalanceBefore())
                .balanceAfter(transaction.getBalanceAfter())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

}
