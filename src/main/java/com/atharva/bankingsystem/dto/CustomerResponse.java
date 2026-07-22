package com.atharva.bankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;
    private String address;
}
