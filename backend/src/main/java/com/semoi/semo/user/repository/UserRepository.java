package com.semoi.semo.user.repository;

import com.semoi.semo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 차현철
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginEmail(String loginEmail);
    Optional<User> findByUsername(String username);

    boolean existsByUserEmail(String email);
}
