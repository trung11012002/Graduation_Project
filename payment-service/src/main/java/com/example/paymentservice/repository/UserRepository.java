package com.example.paymentservice.repository;

import com.example.paymentservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleId(Integer roleId);

    boolean existsByUsername(String username);
}
