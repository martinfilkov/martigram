package com.udemy.martigram.config;

import com.udemy.martigram.dao.RoleRepository;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleInitialization implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByRole(RoleType.USER).isEmpty()) {
            roleRepository.save(new GramRole(RoleType.USER));
        }
        if (roleRepository.findByRole(RoleType.ADMIN).isEmpty()) {
            roleRepository.save(new GramRole(RoleType.ADMIN));
        }
    }
}
