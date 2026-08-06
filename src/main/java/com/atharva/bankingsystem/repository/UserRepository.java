package com.atharva.bankingsystem.repository;

import com.atharva.bankingsystem.entity.User;
import com.atharva.bankingsystem.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    List<User> findByRole(UserRole role);

    Optional<User> findByUsername(String username);
}
