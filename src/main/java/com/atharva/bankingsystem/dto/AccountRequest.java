package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    private Long customerId;

    private AccountType type;
}
