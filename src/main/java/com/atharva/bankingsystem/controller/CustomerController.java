package com.atharva.bankingsystem.controller;

import com.atharva.bankingsystem.dto.CustomerRequest;
import com.atharva.bankingsystem.dto.CustomerResponse;
import com.atharva.bankingsystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create Customer
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    // Get All Customers
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        System.out.println("Inside getAllCustomers()");
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get Customer By ID
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // Update Customer
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLERK', 'CASHIER')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }
}
