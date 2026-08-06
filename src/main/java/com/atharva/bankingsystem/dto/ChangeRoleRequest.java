package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ChangeRoleRequest {

    @NotNull
    private UserRole role;
}
