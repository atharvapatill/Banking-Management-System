package com.atharva.bankingsystem.service;

import com.atharva.bankingsystem.entity.User;
import com.atharva.bankingsystem.dto.UserRequest;
import com.atharva.bankingsystem.dto.UserResponse;
import com.atharva.bankingsystem.enums.UserRole;
import com.atharva.bankingsystem.enums.UserStatus;
import com.atharva.bankingsystem.exception.UserAlreadyExistsException;
import com.atharva.bankingsystem.exception.UserNotFoundException;
import com.atharva.bankingsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            userResponses.add(mapToResponse(user));
        }

        return userResponses;
    }

    public List<UserResponse> getUsersByRole(UserRole role) {

        List<User> users = userRepository.findByRole(role);

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            userResponses.add(mapToResponse(user));
        }

        return userResponses;
    }

    public UserResponse getUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return mapToResponse(user.get());
    }

    public UserResponse changePassword(Long userId, String password) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User existingUser = user.get();

        existingUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(existingUser);

        return mapToResponse(savedUser);
    }

    public UserResponse changeStatus(Long userId, UserStatus status) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User existingUser = user.get();

        existingUser.setStatus(status);

        User savedUser = userRepository.save(existingUser);

        return mapToResponse(savedUser);
    }

    public UserResponse changeRole(Long userId, UserRole role) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User existingUser = user.get();

        existingUser.setRole(role);

        User savedUser = userRepository.save(existingUser);

        return mapToResponse(savedUser);
    }

    // Helper method
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
