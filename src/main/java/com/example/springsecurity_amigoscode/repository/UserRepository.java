package com.example.springsecurity_amigoscode.repository;

import com.example.springsecurity_amigoscode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
