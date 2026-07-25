package com.atharva.bankingsystem.service;

import com.atharva.bankingsystem.dto.AccountRequest;
import com.atharva.bankingsystem.dto.AccountResponse;
import com.atharva.bankingsystem.entity.Account;
import com.atharva.bankingsystem.entity.Customer;
import com.atharva.bankingsystem.enums.AccountStatus;
import com.atharva.bankingsystem.exception.AccountAlreadyClosedException;
import com.atharva.bankingsystem.exception.AccountNotFoundException;
import com.atharva.bankingsystem.exception.CustomerNotFoundException;
import com.atharva.bankingsystem.repository.AccountRepository;
import com.atharva.bankingsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository){
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public AccountResponse createAccount(AccountRequest accountRequest){
        Optional<Customer> customer = customerRepository.findById(accountRequest.getCustomerId());

        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer Not Found");
        }

        Account account = Account.builder()
                .customer(customer.get())
                .type(accountRequest.getType())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .openedAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    public List<AccountResponse> getAllAccounts(){

        List<Account> accounts = accountRepository.findAll();

        List<AccountResponse> accountResponses = new ArrayList<>();

        for (Account account: accounts){
            accountResponses.add(mapToResponse(account));
        }

        return accountResponses;
    }

    public List<AccountResponse> getAllAccountsByCustomerId(Long customerId){
       Optional<Customer> customer = customerRepository.findById(customerId);

       if(customer.isEmpty()){
           throw  new CustomerNotFoundException("Customer Not Found");
       }

       List<Account> accounts = accountRepository.findByCustomer(customer.get());

       List<AccountResponse> accountResponses = new ArrayList<>();

       for (Account account:accounts){
           accountResponses.add(mapToResponse(account));
       }
       return accountResponses;
    }

    public AccountResponse getAccountById(Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new AccountNotFoundException("Account Not Found");
        }

        return mapToResponse(account.get());
    }

    public AccountResponse closeAccount(Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new AccountNotFoundException("Account Not Found");
        }

        Account existingAccount = account.get();

        if (existingAccount.getStatus() == AccountStatus.CLOSED) {
            throw new AccountAlreadyClosedException("Account is already closed");
        }

        existingAccount.setStatus(AccountStatus.CLOSED);

        Account savedAccount = accountRepository.save(existingAccount);

        return mapToResponse(savedAccount);
    }

    // Helper function
    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .customerId(account.getCustomer().getId())
                .accountType(account.getType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .openedAt(account.getOpenedAt())
                .build();
    }
}
