package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<GramRole, Long> {
}
