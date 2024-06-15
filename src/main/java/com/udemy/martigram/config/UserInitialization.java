package com.udemy.martigram.config;

import com.udemy.martigram.dao.RoleRepository;
import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@DependsOn("roleInitialization")
public class UserInitialization implements CommandLineRunner {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserInitialization(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()){
           GramRole admin = roleRepository.findByRole(RoleType.ROLE_ADMIN)
                   .orElseThrow(() ->new RuntimeException("Issue generating roles!"));

            GramRole user = roleRepository.findByRole(RoleType.ROLE_USER)
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
