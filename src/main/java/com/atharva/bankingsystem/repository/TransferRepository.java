package com.atharva.bankingsystem.repository;

import com.atharva.bankingsystem.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
}
