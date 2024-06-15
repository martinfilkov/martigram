package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<GramRole, Long> {
    Optional<GramRole> findByRole(RoleType role);
}
