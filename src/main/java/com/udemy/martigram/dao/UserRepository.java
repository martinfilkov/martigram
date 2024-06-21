package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<GramUser, Long> {
    Optional<GramUser> findByUsername(String username);
    Optional<GramUser> findByEmail(String email);
}
