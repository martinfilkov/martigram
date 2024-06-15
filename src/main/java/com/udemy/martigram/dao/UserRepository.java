package com.udemy.martigram.dao;

import com.udemy.martigram.entity.GramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<GramUser, Long> {
}
