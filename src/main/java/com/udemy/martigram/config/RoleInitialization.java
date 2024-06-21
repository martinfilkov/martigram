package com.udemy.martigram.config;

import com.udemy.martigram.dao.RoleRepository;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitialization implements CommandLineRunner {
    private RoleRepository roleRepository;

    @Autowired
    public RoleInitialization(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRole(RoleType.USER).isEmpty()) {
            roleRepository.save(new GramRole(RoleType.USER));
        }
        if (roleRepository.findByRole(RoleType.ADMIN).isEmpty()) {
            roleRepository.save(new GramRole(RoleType.ADMIN));
        }
    }
}
