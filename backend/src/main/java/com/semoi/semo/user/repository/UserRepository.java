package com.semoi.semo.user.repository;

import com.semoi.semo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginEmail(String loginEmail);
    Optional<User> findByUsername(String username);
}
