package com.atharva.bankingsystem.exception;

public class AccountAlreadyClosedException extends RuntimeException {
    public AccountAlreadyClosedException(String message) {
        super(message);
    }
}
