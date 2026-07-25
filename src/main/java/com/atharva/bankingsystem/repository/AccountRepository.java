package com.atharva.bankingsystem.repository;

import com.atharva.bankingsystem.entity.Account;
import com.atharva.bankingsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByCustomer(Customer customer);
}
