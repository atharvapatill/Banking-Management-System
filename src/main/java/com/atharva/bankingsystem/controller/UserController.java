package com.atharva.bankingsystem.controller;

import com.atharva.bankingsystem.dto.ChangePasswordRequest;
import com.atharva.bankingsystem.dto.ChangeRoleRequest;
import com.atharva.bankingsystem.dto.ChangeStatusRequest;
import com.atharva.bankingsystem.dto.UserRequest;
import com.atharva.bankingsystem.dto.UserResponse;
import com.atharva.bankingsystem.enums.UserRole;
import com.atharva.bankingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {

        UserResponse userResponse = userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {

        UserResponse userResponse = userService.getUserById(userId);

        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable UserRole role) {

        List<UserResponse> users = userService.getUsersByRole(role);

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{userId}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest request) {

        UserResponse userResponse = userService.changePassword(userId, request.getPassword());

        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserResponse> changeStatus(@PathVariable Long userId, @Valid @RequestBody ChangeStatusRequest request) {

        UserResponse userResponse = userService.changeStatus(userId, request.getStatus());

        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/{userId}/role")
    public ResponseEntity<UserResponse> changeRole(@PathVariable Long userId, @Valid @RequestBody ChangeRoleRequest request) {

        UserResponse userResponse = userService.changeRole(userId, request.getRole());

        return ResponseEntity.ok(userResponse);
    }
}