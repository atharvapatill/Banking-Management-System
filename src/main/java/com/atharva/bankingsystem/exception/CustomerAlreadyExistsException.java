package com.atharva.bankingsystem.exception;

public class CustomerAlreadyExistsException extends RuntimeException{
    public CustomerAlreadyExistsException(String message){
        super(message);
    }
}
