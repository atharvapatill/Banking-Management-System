package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.AccountStatus;
import com.atharva.bankingsystem.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long id;

    private Long customerId;

    private AccountType accountType;

    private BigDecimal balance;

    private AccountStatus status;

    private LocalDateTime openedAt;
}
