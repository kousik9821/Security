package com.example.WebSecurity.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.WebSecurity.entity.Role;

@Repository("roleRepository")
@EntityScan("com.example.WebSecurity.entity")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}