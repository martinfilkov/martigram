package com.udemy.martigram.config;

import com.udemy.martigram.dao.RoleRepository;
import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@DependsOn("roleInitialization")
@RequiredArgsConstructor
public class UserInitialization implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(userRepository.findAll().isEmpty()){
           GramRole admin = roleRepository.findByRole(RoleType.ADMIN)
                   .orElseThrow(() ->new RuntimeException("Issue generating roles!"));

            GramRole user = roleRepository.findByRole(RoleType.USER)
                    .orElseThrow(() ->new RuntimeException("Issue generating roles!"));

            GramUser lebron = GramUser.builder()
                    .email("lebron@dunk.com")
                    .username("kingjames")
                    .password(passwordEncoder.encode("sunshine"))
                    .role(admin)
                    .build();

            GramUser musk = GramUser.builder()
                    .email("teslafanboy@tesla.com")
                    .username("elon_musk")
                    .password(passwordEncoder.encode("rockets"))
                    .role(user)
                    .build();

            GramUser cr7 = GramUser.builder()
                    .email("ronaldo@goat.com")
                    .username("ronaldo7")
                    .password(passwordEncoder.encode("portugal2016"))
                    .role(admin)
                    .build();

            GramUser giannis = GramUser.builder()
                    .email("giannis@freak.gr")
                    .username("giannis_an34")
                    .password(passwordEncoder.encode("freakinthesheets"))
                    .role(user)
                    .build();

            userRepository.save(lebron);
            userRepository.save(musk);
            userRepository.save(cr7);
            userRepository.save(giannis);
        }
    }
}
