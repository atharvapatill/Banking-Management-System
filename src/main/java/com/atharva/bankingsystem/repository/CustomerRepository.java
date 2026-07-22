package com.atharva.bankingsystem.repository;

import com.atharva.bankingsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(long phone);

}
