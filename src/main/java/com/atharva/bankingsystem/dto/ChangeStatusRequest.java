package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusRequest {

    @NotNull
    private UserStatus status;
}
