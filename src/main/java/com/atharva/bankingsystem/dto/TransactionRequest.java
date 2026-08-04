package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private TransactionType type;

    @NotNull
    @Positive
    private BigDecimal amount;
}