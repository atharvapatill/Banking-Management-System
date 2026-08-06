package com.atharva.bankingsystem.dto;

import com.atharva.bankingsystem.enums.UserRole;
import com.atharva.bankingsystem.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private UserRole role;

    private UserStatus status;
}
