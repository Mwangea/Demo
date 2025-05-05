package com.authentication.demo.repository;

import com.authentication.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByProviderId(String providerId);
}