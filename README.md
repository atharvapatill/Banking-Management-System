# Banking Management System

## Overview

The Banking Management System is a backend application designed to support the day-to-day operations of a bank.

The system provides a centralized platform for managing **customers, bank accounts, transactions, money transfers, and bank staff**. It also includes a secure access system that ensures employees can only perform the operations appropriate to their role.

The project focuses on making common banking operations organized, controlled, and traceable.

---

## What Does This System Do?

The system allows bank staff to manage the complete lifecycle of a customer's banking activities.

A typical workflow looks like this:

**Customer → Bank Account → Transactions → Money Transfers**

For example, a bank employee can:

1. Register a customer.
2. Create a bank account for the customer.
3. Deposit or withdraw money from the account.
4. View the account balance.
5. View the customer's transaction history.
6. Transfer money between accounts.
7. Review transfer information.
8. Reverse a transfer when authorized.

All of these operations are handled through the banking system.

---

## Main Features

### Customer Management

The system maintains information about bank customers and their accounts.

Bank staff can:

* Create new customers
* Find existing customers
* View customer information
* View the accounts associated with a customer
* Prevent duplicate customer records

This allows customer information to remain organized and connected to their banking accounts.

---

### Account Management

Customers can have bank accounts managed through the system.

The system supports:

* Creating accounts
* Viewing account information
* Checking account balances
* Depositing money
* Withdrawing money
* Managing account status
* Preventing transactions on closed accounts

Different account types and account statuses can be maintained according to the bank's requirements.

---

### Transactions

Every important financial operation is recorded as a transaction.

The system keeps track of activities such as:

* Deposits
* Withdrawals
* Account-related transactions
* Transaction status
* Account balance before a transaction
* Account balance after a transaction

This provides a clear history of how an account's balance has changed over time.

---

### Money Transfers

The system allows authorized bank employees to transfer money between bank accounts.

A transfer involves:

**Source Account → Transfer → Destination Account**

The system also keeps track of the transfer so that it can be reviewed later.

Authorized staff members can also reverse a transfer when required.

---

## Employee Roles

Not every employee should have access to every banking operation.

The system therefore provides different levels of access based on an employee's role.

### Administrator

The Administrator has the highest level of access and is responsible for managing the banking system and its users.

Responsibilities can include:

* Managing employees
* Managing user roles
* Managing user status
* Accessing banking operations
* Performing authorized financial operations

### Manager

Managers have access to important banking operations and are also given permission to perform sensitive actions such as reversing transfers.

### Clerk

Clerks can access customer, account, and transaction information required for their day-to-day responsibilities.

They do not have permission to perform every financial operation.

### Cashier

Cashiers are responsible for operations involving customers' financial activities.

They can perform operations such as:

* Deposits
* Withdrawals
* Authorized money transfers
* Viewing relevant banking information

---

## Security

Security is an important part of the system because it handles financial information and banking operations.

Each employee must log in before accessing protected banking operations.

The system also ensures that employees can only perform actions allowed by their assigned role.

For example:

* A clerk cannot reverse a transfer.
* A cashier cannot manage employee accounts.
* A manager can perform certain sensitive operations.
* An administrator has broader system-level access.

This helps prevent unauthorized operations and protects sensitive banking data.

---

## Transfer Reversal

A transfer may sometimes need to be reversed due to an incorrect transaction or another operational requirement.

The system provides a controlled reversal process.

Only authorized employees can perform this operation.

When a transfer is reversed, the system records the reversal rather than simply removing the original transfer. This helps maintain a clear history of what happened.

---

## Error Handling

The system is designed to handle common banking situations safely.

For example, it can identify situations such as:

* Customer does not exist
* Account does not exist
* User does not exist
* Account has already been closed
* Insufficient account balance
* Duplicate customer
* Duplicate employee
* Invalid login credentials
* Disabled employee account
* Transaction does not exist
* Transfer does not exist

Instead of allowing these situations to cause unexpected behavior, the system provides appropriate responses to the user.

---

## Typical Banking Workflow

A simplified example of using the system:

### 1. Employee Login

A bank employee logs into the system.

### 2. Customer Registration

If the customer is new, their information is added to the system.

### 3. Account Creation

A bank account is created and associated with the customer.

### 4. Banking Operations

The employee can perform permitted operations such as depositing or withdrawing money.

### 5. Transaction History

Each financial operation becomes part of the account's transaction history.

### 6. Money Transfer

If required, money can be transferred from one account to another by an authorized employee.

### 7. Transfer Management

The transfer can later be reviewed, and authorized staff can reverse it when necessary.

---

## Why This Project?

The goal of this project is to demonstrate how a real-world banking system can organize its core operations while maintaining:

* Secure employee access
* Controlled financial operations
* Clear transaction records
* Customer and account management
* Role-based responsibilities
* Reliable handling of banking errors

Rather than treating banking operations as independent actions, the system connects customers, accounts, transactions, transfers, and employees into one organized platform.

---

## Project Scope

The current version focuses on the **core backend operations of a banking management system**.

It includes:

* Employee authentication
* Employee role management
* Customer management
* Account management
* Deposits and withdrawals
* Transaction tracking
* Account-to-account transfers
* Transfer reversal
* Access control
* Error handling

The project is currently focused on the backend and does not include a customer-facing or employee-facing graphical interface.

---

## Future Improvements

Possible future additions include:

* Audit logs
* Notifications
* Loan management
* Interest calculation
