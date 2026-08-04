package com.atharva.bankingsystem.dto;
import com.atharva.bankingsystem.enums.TransferStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    private Long id;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    private TransferStatus status;

    private Long debitTransactionId;

    private Long creditTransactionId;

    private LocalDateTime createdAt;
}