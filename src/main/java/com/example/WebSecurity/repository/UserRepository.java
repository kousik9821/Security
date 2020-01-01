package com.example.WebSecurity.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.WebSecurity.entity.User;

@Repository("userRepository")
@EntityScan("com.example.WebSecurity.entity")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}