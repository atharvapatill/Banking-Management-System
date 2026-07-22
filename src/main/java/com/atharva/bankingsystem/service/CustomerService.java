package com.atharva.bankingsystem.service;

import com.atharva.bankingsystem.dto.CustomerRequest;
import com.atharva.bankingsystem.dto.CustomerResponse;
import com.atharva.bankingsystem.entity.Customer;
import com.atharva.bankingsystem.exception.CustomerAlreadyExistsException;
import com.atharva.bankingsystem.exception.CustomerNotFoundException;
import com.atharva.bankingsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    //Create Customer
    public CustomerResponse createCustomer(CustomerRequest customerRequest){

        if(customerRepository.existsByEmail(customerRequest.getEmail())){
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }

        if(customerRepository.existsByPhone(customerRequest.getPhone())){
            throw new CustomerAlreadyExistsException("Customer with this Phone Number already exists");
        }

        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .address(customerRequest.getAddress())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);

    }

    //Get All Customers
    public List<CustomerResponse> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (Customer customer: customers){
            customerResponses.add(mapToResponse(customer));
        }

        return customerResponses;
    }

    //Get Customer By ID
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        return mapToResponse(customer);
    }

    //Update Customer
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        // Check if email is being changed and already exists
        if (!customer.getEmail().equals(customerRequest.getEmail()) && customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }

        // Check if phone is being changed and already exists
        if (!customer.getPhone().equals(customerRequest.getPhone()) && customerRepository.existsByPhone(customerRequest.getPhone())) {
            throw new CustomerAlreadyExistsException("Customer with this phone number already exists");
        }

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setAddress(customerRequest.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    // Helper method
    private CustomerResponse mapToResponse(Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }

}
